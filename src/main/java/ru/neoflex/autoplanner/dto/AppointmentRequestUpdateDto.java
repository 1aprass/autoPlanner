package ru.neoflex.autoplanner.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestUpdateDto {
    private String status;
    private String comment;
}