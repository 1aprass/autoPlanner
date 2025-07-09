package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DocumentResponseDto>>> getDocuments(
            @RequestParam(value = "user_id", required = false) Long userId) {


        List<DocumentResponseDto> documents = documentService.getAllDocumentsByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success(documents));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<DocumentResponseDto>> addDocument(@Valid @RequestBody DocumentRequestDto dto) {

        DocumentResponseDto saved = documentService.addDocument(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Document added successfully", saved));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<DocumentResponseDto>> updateDocument(
            @Valid @PathVariable Long id, @RequestBody DocumentUpdateRequestDto dto) {

        DocumentResponseDto updated = documentService.updateDocument(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Document updated successfully", updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponseDto.success("Document deleted successfully"));

    }
}
