package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
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

        var result = service.getById(id);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> create(
            @Valid @RequestBody ServiceCenterRequestDto dto) {

        var result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Service center added successfully", result));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> update(
            @Valid
            @PathVariable Long id,
            @RequestBody ServiceCenterUpdateRequestDto dto) {
        var result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Service center updated successfully", result));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Service center deleted successfully"));

    }
}
