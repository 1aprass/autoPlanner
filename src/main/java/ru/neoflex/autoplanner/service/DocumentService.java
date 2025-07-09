package ru.neoflex.autoplanner.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<DocumentResponseDto> getAllDocumentsByUserId(Long userId) {
        List<Document> documents = documentRepository.findByUserId(userId);
        if (documents.isEmpty()) {
            throw new EntityNotFoundException("No documents found for user with id: " + userId);
        }
        return documents.stream().map(documentMapper::toDto).toList();
    }

    @Transactional
    public DocumentResponseDto addDocument(DocumentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));
        Document document = documentMapper.toEntity(dto);
        document.setUser(user);
        Document saved = documentRepository.save(document);
        return documentMapper.toDto(saved);
    }

    @Transactional
    public DocumentResponseDto updateDocument(Long id, DocumentUpdateRequestDto dto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));
        documentMapper.updateEntityFromDto(document, dto);
        Document updated = documentRepository.save(document);
        return documentMapper.toDto(updated);
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}