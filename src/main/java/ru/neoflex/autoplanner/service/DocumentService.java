package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.DocumentRequestDto;
import ru.neoflex.autoplanner.dto.DocumentResponseDto;
import ru.neoflex.autoplanner.dto.DocumentUpdateRequestDto;
import ru.neoflex.autoplanner.entity.Document;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.DocumentMapper;
import ru.neoflex.autoplanner.repository.DocumentRepository;
import ru.neoflex.autoplanner.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    public List<DocumentResponseDto> getAllDocumentsByUserId(Long userId) {
        List<Document> documents = documentRepository.findByUserId(userId);
        if (documents.isEmpty()) throw new RuntimeException("Documents not found for user " + userId);
        return documents.stream().map(documentMapper::toDto).toList();
    }

    public DocumentResponseDto addDocument(DocumentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Document document = documentMapper.toEntity(dto);
        document.setUser(user);
        Document saved = documentRepository.save(document);
        return documentMapper.toDto(saved);
    }

    public DocumentResponseDto updateDocument(Long id, DocumentUpdateRequestDto dto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        documentMapper.updateEntityFromDto(document, dto);
        Document updated = documentRepository.save(document);
        return documentMapper.toDto(updated);
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new RuntimeException("Document not found");
        }
        documentRepository.deleteById(id);
    }
}