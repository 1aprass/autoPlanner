package ru.neoflex.autoplanner.dto;

import lombok.Data;

@Data
public class DocumentUpdateRequestDto {
    private String name;
    private String fileUrl;
}
