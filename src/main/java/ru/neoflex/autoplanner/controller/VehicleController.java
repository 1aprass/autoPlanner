package ru.neoflex.autoplanner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.VehicleService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")

public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Adding a new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle added successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data in request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User with the specified user_id not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> createVehicle(@Valid @RequestBody VehicleCreateRequestDto dto) {

        VehicleResponseDto response = vehicleService.createVehicle(dto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success("Vehicle created successfully", response));

    }

    @Operation(summary = "Getting information about a car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle data returned",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle with specified ID not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> getVehicle(@Valid @PathVariable Long id) {

        VehicleResponseDto response = vehicleService.getVehicle(id);
        return ResponseEntity.ok(ApiResponseDto.success(response));

    }
    @Operation(summary = "Updating vehicle information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle information successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle with specified ID not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> updateVehicle(
            @PathVariable Long id, @Valid @RequestBody VehicleUpdateRequestDto dto) {

        VehicleResponseDto response = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Vehicle updated successfully", response));

    }


    @Operation(summary = "Delete a car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The vehicle successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle with specified ID not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(ApiResponseDto.success("Vehicle deleted successfully"));

    }
}
