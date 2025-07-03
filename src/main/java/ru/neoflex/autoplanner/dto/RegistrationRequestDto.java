package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
}
