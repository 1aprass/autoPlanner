package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequestDto {
    @NotBlank(message = "make is required")
    public String make;

    @NotBlank(message = "model is required")
    public String model;

    @Min(value = 1950, message = "year should be valid")
    public int year;

    @NotBlank(message = "licensePlate is required")
    public String licensePlate;

    @Min(value = 0, message = "currentOdometer should be positive")
    public int currentOdometer;

    @NotNull(message = "userId is required")
    public Long userId;
}
