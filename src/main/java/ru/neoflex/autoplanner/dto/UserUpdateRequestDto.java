package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @Email(message = "email should be valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "role is required")
    private String role;
}