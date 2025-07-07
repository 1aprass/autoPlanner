package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairTypeRequestDto {
    private String name;
    private String category;
}