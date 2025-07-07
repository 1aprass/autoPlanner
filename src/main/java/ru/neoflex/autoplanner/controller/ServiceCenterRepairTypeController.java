package ru.neoflex.autoplanner.controller;

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
    public ResponseEntity<?> getByServiceCenter(@RequestParam(value = "service_center_id", required = false) Long serviceCenterId) {
        try {
            List<ServiceCenterRepairTypeResponseDto> result = service.getByServiceCenter(serviceCenterId);
            return ResponseEntity.ok(ApiResponseDto.success(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDto.error("Failed to fetch data"));
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ServiceCenterRepairTypeRequestDto dto) {
        try {
            ServiceCenterRepairTypeResponseDto result = service.add(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success("Service center repair type added successfully", result));
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDto.error("Failed to add service center repair type"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponseDto.success("Service center repair type deleted successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDto.error("Failed to delete service center repair type"));
        }
    }
}