package ru.neoflex.autoplanner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_center_repair_type")
public class ServiceCenterRepairType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_center_id", nullable = false)
    private ServiceCenter serviceCenter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "repair_type_id", nullable = false)
    private RepairType repairType;

}