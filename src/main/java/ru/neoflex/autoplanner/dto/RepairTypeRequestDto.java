package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairTypeRequestDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "category is required")
    private String category;
}