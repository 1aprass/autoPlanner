package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateRequestDto {
    @NotBlank(message = "make is required")
    private String make;

    @NotBlank(message = "model is required")
    private String model;

    @Min(value = 1886, message = "year should be valid")
    private int year;

    @NotBlank(message = "licensePlate is required")
    private String licensePlate;

    @Min(value = 0, message = "currentOdometer should be positive")
    private int currentOdometer;
}