package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSnapshotUpdateDto {
    @NotNull(message = "totalSpent is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "totalSpent must be positive or zero")
    private BigDecimal totalSpent;

    @NotNull(message = "serviceCount is required")
    @Min(value = 0, message = "serviceCount must be zero or more")
    private Integer serviceCount;

    @NotNull(message = "mostCommonRepairTypeId is required")
    private Long mostCommonRepairTypeId;
}