package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterUpdateRequestDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "phone is required")
    private String phone;

    @DecimalMin(value = "0.0", inclusive = true, message = "rating must be positive or zero")
    private BigDecimal rating;
}