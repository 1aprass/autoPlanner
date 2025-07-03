package ru.neoflex.autoplanner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles", uniqueConstraints = @UniqueConstraint(columnNames = "license_plate"))
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String make;

    @Column(nullable = false, length = 100)
    private String model;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int year;

    @Column(name = "license_plate", nullable = false, length = 20, unique = true)
    private String licensePlate;

    @Column(name = "current_odometer", nullable = false, updatable = true)
    private int currentOdometer;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vehicle")
    private List<ServiceHistory> serviceHistories;

    @OneToMany(mappedBy = "vehicle")
    private List<Reminder> reminders;

    @OneToMany(mappedBy = "vehicle")
    private List<Document> documents;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
