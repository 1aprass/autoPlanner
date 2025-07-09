package ru.neoflex.autoplanner.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestResponseDto {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private Long serviceCenterId;
    private LocalDateTime requestedDate;
    private String status;
    private String comment;
}