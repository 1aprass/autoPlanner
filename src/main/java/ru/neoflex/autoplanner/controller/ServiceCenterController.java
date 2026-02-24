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
import ru.neoflex.autoplanner.service.ServiceCenterService;

@RestController
@RequestMapping("/api/v1/service_centers")
@RequiredArgsConstructor
public class ServiceCenterController {

    private final ServiceCenterService service;

    @Operation(summary = "Get information about the service center by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service center data successfully received",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data in request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Service center not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> getById(@PathVariable Long id) {

        var result = service.getById(id);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @Operation(summary = "Add new service center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service center successfully added",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "The service center already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> create(
            @Valid @RequestBody ServiceCenterRequestDto dto) {

        var result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Service center added successfully", result));

    }

    @Operation(summary = "Update service center information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information updated successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Service center not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> update(
            @Valid
            @PathVariable Long id,
            @RequestBody ServiceCenterUpdateRequestDto dto) {
        var result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Service center updated successfully", result));

    }

    @Operation(summary = "Delete service center by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service center successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Service center not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Service center deleted successfully"));

    }
}
