package ru.neoflex.autoplanner.controller;

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
    public ResponseEntity<?> getDocuments(@RequestParam(value = "user_id", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error("Missing required parameter: user_id"));
        }
        try {
            List<DocumentResponseDto> documents = documentService.getAllDocumentsByUserId(userId);
            return ResponseEntity.ok(ApiResponseDto.success(documents));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addDocument(@RequestBody DocumentRequestDto dto) {
        try {
            DocumentResponseDto saved = documentService.addDocument(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Document added successfully", saved));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Long id, @RequestBody DocumentUpdateRequestDto dto) {
        try {
            DocumentResponseDto updated = documentService.updateDocument(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Document updated successfully", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok(ApiResponseDto.success("Document deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }
}
