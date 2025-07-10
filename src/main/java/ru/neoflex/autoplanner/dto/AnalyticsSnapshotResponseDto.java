package ru.neoflex.autoplanner.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSnapshotResponseDto {
    private Long id;
    private Long userId;
    private Integer month;
    private BigDecimal totalSpent;
    private Integer serviceCount;
    private Long mostCommonRepairTypeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
