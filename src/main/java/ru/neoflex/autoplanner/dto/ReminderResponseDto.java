package ru.neoflex.autoplanner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderResponseDto {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private String type;
    private LocalDateTime remindDate;
    private boolean isSent;
    private String notes;
    private int repeatIntervalDays;
    private boolean isRecurring;
}