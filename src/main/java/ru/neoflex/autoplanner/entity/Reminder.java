package ru.neoflex.autoplanner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "reminder_type", nullable = false, length = 100)
    private String reminderType;

    @Column(name = "remind_date", nullable = false)
    private LocalDateTime remindDate;

    @Column(name = "is_sent", nullable = false)
    private boolean isSent = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name="repeat_interval_days",nullable = false)
    private int repeatIntervalDays;

    @Column(name="is_recurring", nullable = false)
    private boolean isRecurring;
}
