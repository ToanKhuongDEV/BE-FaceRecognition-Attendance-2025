package com.example.befacerecognitionattendance2025.domain.entity;


import com.example.befacerecognitionattendance2025.constant.Gender;
import com.example.befacerecognitionattendance2025.constant.Role;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.List;

@Table(name = "employee")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "employee_code", unique = true, nullable = false, updatable = false)
    private Integer employeeCode;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String avatar;

    @Column(length = 1000)
    private String faceId;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;
}

