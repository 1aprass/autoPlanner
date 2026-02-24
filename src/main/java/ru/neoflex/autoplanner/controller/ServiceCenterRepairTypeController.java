package ru.neoflex.autoplanner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiErrorDto;
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

    @Operation(summary = "Getting all types of repairs for the service center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service services successfully received",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data in request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Services not found for the specified service center",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ServiceCenterRepairTypeResponseDto>>> getByServiceCenter(
            @RequestParam(value = "service_center_id") @NotNull Long serviceCenterId) {

        List<ServiceCenterRepairTypeResponseDto> result = service.getByServiceCenter(serviceCenterId);
        return ResponseEntity.ok(ApiResponseDto.success(result));

    }

    @Operation(summary = "Adding a new service to the service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service added successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "The service already exists for this service center",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ServiceCenterRepairTypeResponseDto>> add(
            @Valid @RequestBody ServiceCenterRepairTypeRequestDto dto) {

        ServiceCenterRepairTypeResponseDto result = service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto
                .success("Service center repair type added successfully", result));

    }

    @Operation(summary = "Delete a service by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The service successfully Deleted.",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Service with the specified ID not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success("Service center repair type deleted successfully"));

    }
}