package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @Email(message = "email should be valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "passwordHash is required")
    private String passwordHash;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "role is required")
    private String role;
}