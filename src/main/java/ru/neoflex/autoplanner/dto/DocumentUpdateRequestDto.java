package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentUpdateRequestDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "fileUrl is required")
    private String fileUrl;
}
