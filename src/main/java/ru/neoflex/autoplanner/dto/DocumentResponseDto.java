package ru.neoflex.autoplanner.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentResponseDto {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private String name;
    private String type;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}