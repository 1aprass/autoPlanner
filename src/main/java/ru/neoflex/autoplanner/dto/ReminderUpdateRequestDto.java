package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderUpdateRequestDto {
    @NotBlank(message = "type is required")
    private String type;

    @NotNull(message = "remindDate is required")
    private LocalDateTime remindDate;

    private boolean notified;
}