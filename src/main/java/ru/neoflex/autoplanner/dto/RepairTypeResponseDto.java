package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairTypeResponseDto {
    private Long id;
    private String name;
    private String category;
}