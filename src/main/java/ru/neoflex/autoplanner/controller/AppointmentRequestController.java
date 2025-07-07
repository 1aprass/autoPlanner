package ru.neoflex.autoplanner.controller;

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
@RequestMapping("/api/v1/appointment_requests")
@RequiredArgsConstructor
public class AppointmentRequestController {

    private final AppointmentRequestService service;

    @GetMapping
    public ResponseEntity<?> getByUserId(@RequestParam(name = "user_id") Long userId) {
        try {
            List<AppointmentRequestResponseDto> result = service.getByUserId(userId);
            return ResponseEntity.ok(ApiResponseDto.success(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentRequestRequestDto dto) {
        try {
            var result = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Appointment request created successfully", result));
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.error("Invalid input"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody AppointmentRequestUpdateDto dto) {
        try {
            var result = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Appointment request updated successfully", result));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.error("Invalid input"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponseDto.success("Appointment request deleted successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        }
    }
}