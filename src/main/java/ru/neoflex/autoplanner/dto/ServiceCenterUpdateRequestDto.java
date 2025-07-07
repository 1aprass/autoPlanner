package ru.neoflex.autoplanner.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterUpdateRequestDto {
    private String name;
    private String address;
    private String phone;
    private BigDecimal rating;
}