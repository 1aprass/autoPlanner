package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {
    private String firstName;
    private String lastName;
    private String email;
}