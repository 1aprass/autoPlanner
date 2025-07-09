package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentRequestDto {
    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "vehicleId is required")
    private Long vehicleId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "fileUrl is required")
    private String fileUrl;

    @NotNull(message = "uploadedAt is required")
    private LocalDateTime uploadedAt;
}