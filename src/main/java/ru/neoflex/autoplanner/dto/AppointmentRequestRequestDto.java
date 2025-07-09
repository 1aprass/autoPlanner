package ru.neoflex.autoplanner.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestRequestDto {
    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "vehicleId is required")
    private Long vehicleId;

    @NotNull(message = "serviceCenterId is required")
    private Long serviceCenterId;

    @NotNull(message = "requestedDate is required")
    private LocalDateTime requestedDate;

    @NotBlank(message = "status is required")
    private String status;

    private String comment;
}