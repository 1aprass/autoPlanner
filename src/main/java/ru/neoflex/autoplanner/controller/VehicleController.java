package ru.neoflex.autoplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.service.VehicleService;


@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody VehicleCreateRequestDto dto) {
        try {
            VehicleResponseDto response = vehicleService.createVehicle(dto);
            return ResponseEntity.status(201)
                    .body(ApiResponseDto.success("Vehicle created successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicle(@PathVariable Long id) {
        try {
            VehicleResponseDto response = vehicleService.getVehicle(id);
            return ResponseEntity.ok(ApiResponseDto.success(response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody VehicleUpdateRequestDto dto) {
        try {
            VehicleResponseDto response = vehicleService.updateVehicle(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("Vehicle updated successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok(ApiResponseDto.success("Vehicle deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }
}
