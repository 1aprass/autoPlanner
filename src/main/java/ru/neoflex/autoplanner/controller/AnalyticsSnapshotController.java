package ru.neoflex.autoplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotRequestDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotResponseDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotUpdateDto;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.service.AnalyticsSnapshotService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/analytics_snapshots")
@RequiredArgsConstructor
public class AnalyticsSnapshotController {

    private final AnalyticsSnapshotService service;

    @GetMapping
    public ResponseEntity<?> getByUserId(@RequestParam("user_id") Long userId) {
        try {
            List<AnalyticsSnapshotResponseDto> result = service.getByUserId(userId);
            return ResponseEntity.ok(ApiResponseDto.success(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AnalyticsSnapshotRequestDto dto) {
        try {
            AnalyticsSnapshotResponseDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Analytics snapshot created successfully", created));
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.error("Invalid input"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AnalyticsSnapshotUpdateDto dto) {
        try {
            AnalyticsSnapshotResponseDto updated = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot updated successfully", updated));
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
            return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot deleted successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error(e.getMessage()));
        }
    }
}