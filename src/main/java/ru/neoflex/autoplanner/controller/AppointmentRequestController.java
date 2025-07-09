package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.AppointmentRequestRequestDto;
import ru.neoflex.autoplanner.dto.AppointmentRequestResponseDto;
import ru.neoflex.autoplanner.dto.AppointmentRequestUpdateDto;
import ru.neoflex.autoplanner.service.AppointmentRequestService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointment_requests")
public class AppointmentRequestController {

    private final AppointmentRequestService service;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<AppointmentRequestResponseDto>>> getByUserId(
            @RequestParam(name = "user_id") @NotNull Long userId) {

        List<AppointmentRequestResponseDto> result = service.getByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<AppointmentRequestResponseDto>> create(
            @Valid @RequestBody AppointmentRequestRequestDto dto) {

        AppointmentRequestResponseDto result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Appointment request created successfully", result));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AppointmentRequestResponseDto>> update(@Valid @PathVariable Long id,
                                    @RequestBody AppointmentRequestUpdateDto dto) {

        AppointmentRequestResponseDto result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponseDto
                .success("Appointment request updated successfully", result));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

            service.delete(id);
            return ResponseEntity.ok(ApiResponseDto
                    .success("Appointment request deleted successfully"));
    }
}