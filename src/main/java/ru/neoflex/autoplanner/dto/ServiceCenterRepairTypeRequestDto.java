package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterRepairTypeRequestDto {
    @NotNull(message = "serviceCenterId is required")
    private Long serviceCenterId;

    @NotNull(message = "repairTypeId is required")
    private Long repairTypeId;

    @NotNull(message = "price is required")
    private int price;

    @NotBlank(message = "currency is required")
    private String currency;
}
