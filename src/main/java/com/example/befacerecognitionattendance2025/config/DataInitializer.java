package com.example.befacerecognitionattendance2025.config;


import com.example.befacerecognitionattendance2025.constant.Gender;
import com.example.befacerecognitionattendance2025.constant.Role;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import com.example.befacerecognitionattendance2025.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner initManager() {
        return args -> {
            if (employeeRepository.count() == 0) {
                Employee manager = Employee.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("ToanAbc!23"))
                        .fullName("Default Manager")
                        .gender(Gender.MALE)
                        .role(Role.MANAGER)
                        .dateBirth(LocalDate.of(1990, 1, 1))
                        .email("manager@example.com")
                        .phoneNumber("0123456789")
                        .avatar(null)
                        .build();
                employeeRepository.save(manager);
            }
        };
    }
}