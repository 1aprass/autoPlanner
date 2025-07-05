package ru.neoflex.autoplanner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderRequestDto {
    private Long userId;
    private String type;
    private LocalDateTime remindDate;
    private boolean notified;
}
