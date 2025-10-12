package com.example.befacerecognitionattendance2025.config;

import com.example.befacerecognitionattendance2025.constant.Gender;
import com.example.befacerecognitionattendance2025.constant.Role;
import com.example.befacerecognitionattendance2025.domain.entity.Department;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import com.example.befacerecognitionattendance2025.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initManager() {
        return args -> {

            Department hrDepartment = departmentRepository.findByName("Phòng Nhân Sự")
                    .orElseGet(() -> {
                        Department dep = Department.builder()
                                .name("Phòng Nhân Sự")
                                .baseSalary(10000000.0)
                                .description("Phụ trách công tác nhân sự và tiền lương")
                                .build();
                        return departmentRepository.save(dep);
                    });

            // 2. Tạo tài khoản admin nếu chưa tồn tại
            if (employeeRepository.findByUsername("admin").isEmpty()) {
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
                        .department(hrDepartment)
                        .build();
                employeeRepository.save(manager);
            }
        };
    }
}