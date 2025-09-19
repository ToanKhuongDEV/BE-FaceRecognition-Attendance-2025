package com.example.befacerecognitionattendance2025.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payroll",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "month", "year"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Integer month;
    private Integer year;
    private Double totalHours;
    private Double baseSalary;
    private Double bonus;
    private Double deduction;
    private Double finalSalary;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
