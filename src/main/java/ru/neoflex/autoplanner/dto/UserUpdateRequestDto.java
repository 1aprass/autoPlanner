package ru.neoflex.autoplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}