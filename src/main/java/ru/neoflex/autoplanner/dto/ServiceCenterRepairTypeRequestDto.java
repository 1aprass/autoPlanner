package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterRepairTypeRequestDto {
    private Long serviceCenterId;
    private Long repairTypeId;
}
