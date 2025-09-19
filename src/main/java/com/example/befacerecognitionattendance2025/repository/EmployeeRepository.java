package com.example.befacerecognitionattendance2025.repository;

import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Optional<Employee> findByUsername(String username);
}
