package ru.neoflex.autoplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterUpdateRequestDto;
import ru.neoflex.autoplanner.service.ServiceCenterService;

@RestController
@RequestMapping("/api/v1/service_centers")
@RequiredArgsConstructor
public class ServiceCenterController {

    private final ServiceCenterService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> getById(@PathVariable Long id) {
        try {
            var result = service.getById(id);
            return ResponseEntity.ok(ApiResponseDto.success(result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> create(@RequestBody ServiceCenterRequestDto dto) {
        try {
            var result = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Service center added successfully", result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> update(
            @PathVariable Long id,
            @RequestBody ServiceCenterUpdateRequestDto dto) {
        try {
            var result = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Service center updated successfully", result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponseDto.success("Service center deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        }
    }
}
