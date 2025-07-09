package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponseDto<List<RepairTypeResponseDto>>> getAll() {

        List<RepairTypeResponseDto> list = service.getAll();
        return ResponseEntity.ok(ApiResponseDto.success(list));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> create(
            @Valid @RequestBody RepairTypeRequestDto dto) {

        RepairTypeResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Repair type added successfully", created));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> update(
            @Valid @PathVariable Long id, @RequestBody RepairTypeRequestDto dto) {

        RepairTypeResponseDto updated = service.update(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Repair type updated successfully", updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Repair type deleted successfully"));

    }
}