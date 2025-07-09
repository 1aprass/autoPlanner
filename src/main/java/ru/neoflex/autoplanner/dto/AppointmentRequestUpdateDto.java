package ru.neoflex.autoplanner.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestUpdateDto {
    @NotBlank(message = "status is required")
    private String status;
    private String comment;
}