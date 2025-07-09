package ru.neoflex.autoplanner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderResponseDto {
    private Long id;
    private Long userId;
    private String type;
    private LocalDateTime remindDate;
    private boolean notified;
}