package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateRequestDto {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int currentOdometer;
}