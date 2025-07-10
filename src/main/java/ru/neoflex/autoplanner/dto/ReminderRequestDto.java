package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderRequestDto {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "vehicleId is required")
    private Long vehicleId;

    @NotBlank(message = "type is required")
    private String type;

    @NotNull(message = "remindDate is required")
    private LocalDateTime remindDate;

    @NotNull(message = "isSent is required")
    private boolean isSent;

    @NotBlank(message = "notes is required")
    private String notes;

    @NotNull(message = "repeatIntervalDays is required")
    private int repeatIntervalDays;

    @NotNull(message = "isRecurring is required")
    private boolean isRecurring;
}
