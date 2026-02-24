package ru.neoflex.autoplanner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.AnalyticsSnapshotService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/analytics_snapshots")
@RequiredArgsConstructor
public class AnalyticsSnapshotController {

    private final AnalyticsSnapshotService service;

    @Operation(summary = "Obtaining user analytics snapshots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "snapshots were successfully received",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "snapshots not found for the specified user",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<AnalyticsSnapshotResponseDto>>> getByUserId(@RequestParam("user_id") Long userId) {

        List<AnalyticsSnapshotResponseDto> result = service.getByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @Operation(summary = "Create user analytics snapshots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "snapshot created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Пuser not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> create(@Valid @RequestBody AnalyticsSnapshotRequestDto dto) {

        AnalyticsSnapshotResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Analytics snapshot created successfully", created));

    }

    @Operation(summary = "Updating the analytical snapshot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Snapshot updated successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Snapshot not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AnalyticsSnapshotResponseDto>> update(
            @PathVariable Long id, @Valid @RequestBody AnalyticsSnapshotUpdateDto dto) {

            AnalyticsSnapshotResponseDto updated = service.update(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot updated successfully", updated));

    }

    @Operation(summary = "Deleting an analytical snapshot by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Snapshot deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Snapshot not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Analytics snapshot deleted successfully"));

    }
}