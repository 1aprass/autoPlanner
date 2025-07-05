package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequestDto {
    public String make;
    public String model;
    public int year;
    public String licensePlate;
    public int currentOdometer;
    public Long userId;
}
