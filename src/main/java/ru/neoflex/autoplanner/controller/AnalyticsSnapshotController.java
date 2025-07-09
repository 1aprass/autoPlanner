package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponseDto<List<AnalyticsSnapshotResponseDto>>> getByUserId(@RequestParam("user_id") Long userId) {

        List<AnalyticsSnapshotResponseDto> result = service.getByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> create(@Valid @RequestBody AnalyticsSnapshotRequestDto dto) {

        AnalyticsSnapshotResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Analytics snapshot created successfully", created));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> update(
            @Valid @PathVariable Long id, @RequestBody AnalyticsSnapshotUpdateDto dto) {

            AnalyticsSnapshotResponseDto updated = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot updated successfully", updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot deleted successfully"));

    }
}