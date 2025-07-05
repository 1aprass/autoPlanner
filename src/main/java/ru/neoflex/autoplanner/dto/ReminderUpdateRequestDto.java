package ru.neoflex.autoplanner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderUpdateRequestDto {
    private String type;
    private LocalDateTime remindDate;
    private boolean notified;
}