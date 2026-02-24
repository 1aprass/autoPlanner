package ru.neoflex.autoplanner.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApiErrorDto {
    @Schema
    private String status;

    @Schema
    private String message;
}
