package ru.neoflex.autoplanner.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDto {
    private Long id;
    private Long userId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int currentOdometer;
    private LocalDateTime createdAt;
}