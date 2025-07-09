package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.service.VehicleService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> createVehicle(@Valid @RequestBody VehicleCreateRequestDto dto) {

        VehicleResponseDto response = vehicleService.createVehicle(dto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success("Vehicle created successfully", response));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> getVehicle(@Valid @PathVariable Long id) {

        VehicleResponseDto response = vehicleService.getVehicle(id);
        return ResponseEntity.ok(ApiResponseDto.success(response));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<VehicleResponseDto>> updateVehicle(
            @Valid @PathVariable Long id, @RequestBody VehicleUpdateRequestDto dto) {

        VehicleResponseDto response = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("Vehicle updated successfully", response));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(ApiResponseDto.success("Vehicle deleted successfully"));

    }
}
