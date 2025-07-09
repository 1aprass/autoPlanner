package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String createdAt;
    private String email;
}