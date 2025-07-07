package ru.neoflex.autoplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.RepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.RepairTypeResponseDto;
import ru.neoflex.autoplanner.service.RepairTypeService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/repair_types")
@RequiredArgsConstructor
public class RepairTypeController {

    private final RepairTypeService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<RepairTypeResponseDto> list = service.getAll();
            return ResponseEntity.ok(ApiResponseDto.success(list));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RepairTypeRequestDto dto) {
        try {
            RepairTypeResponseDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Repair type added successfully", created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Failed to create repair type"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RepairTypeRequestDto dto) {
        try {
            RepairTypeResponseDto updated = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Repair type updated successfully", updated));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Failed to update repair type"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponseDto.success("Repair type deleted successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Failed to delete repair type"));
        }
    }
}