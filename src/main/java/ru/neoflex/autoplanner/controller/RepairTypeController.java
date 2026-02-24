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
import ru.neoflex.autoplanner.dto.ApiErrorDto;
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

    @Operation(summary = "Get all types of repairs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair types successfully received",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Repair types not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<RepairTypeResponseDto>>> getAll() {

        List<RepairTypeResponseDto> list = service.getAll();
        return ResponseEntity.ok(ApiResponseDto.success(list));

    }

    @Operation(summary = "Add a new repair type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Repair type added successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> create(
            @Valid @RequestBody RepairTypeRequestDto dto) {

        RepairTypeResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Repair type added successfully", created));

    }

    @Operation(summary = "Update repair type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair type successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Repair type not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> update(
            @PathVariable Long id, @Valid @RequestBody RepairTypeRequestDto dto) {

        RepairTypeResponseDto updated = service.update(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Repair type updated successfully", updated));

    }

    @Operation(summary = "Delete repair type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair type successfully removed",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Repair type not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Repair type deleted successfully"));

    }
}