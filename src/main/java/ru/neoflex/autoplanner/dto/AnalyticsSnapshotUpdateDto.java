package ru.neoflex.autoplanner.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSnapshotUpdateDto {
    private BigDecimal totalSpent;
    private Integer serviceCount;
    private Long mostCommonRepairTypeId;
}