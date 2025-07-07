package ru.neoflex.autoplanner.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSnapshotRequestDto {
    private Long userId;
    private Integer year;
    private BigDecimal totalSpent;
    private Integer serviceCount;
    private Long mostCommonRepairTypeId;
}
