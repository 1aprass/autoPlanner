package ru.neoflex.autoplanner.dto;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phone;
    private String createdAt;
}