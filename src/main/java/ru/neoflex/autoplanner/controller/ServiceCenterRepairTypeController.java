package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeResponseDto;
import ru.neoflex.autoplanner.service.ServiceCenterRepairTypeService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/service_center_repair_types")
@RequiredArgsConstructor
public class ServiceCenterRepairTypeController {

    private final ServiceCenterRepairTypeService service;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ServiceCenterRepairTypeResponseDto>>> getByServiceCenter(
            @RequestParam(value = "service_center_id") @NotNull Long serviceCenterId) {

        List<ServiceCenterRepairTypeResponseDto> result = service.getByServiceCenter(serviceCenterId);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ServiceCenterRepairTypeResponseDto>> add(
            @Valid @RequestBody ServiceCenterRepairTypeRequestDto dto) {

        ServiceCenterRepairTypeResponseDto result = service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto
                .success("Service center repair type added successfully", result));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Service center repair type deleted successfully"));

    }
}