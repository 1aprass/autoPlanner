package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterRepairTypeResponseDto {
    private Long serviceCenterId;
    private Long repairTypeId;
    private String repairTypeName;
    private int price;
    private String currency;
}