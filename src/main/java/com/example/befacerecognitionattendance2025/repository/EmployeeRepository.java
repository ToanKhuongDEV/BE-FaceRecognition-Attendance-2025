package com.example.befacerecognitionattendance2025.repository;

import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String>, UserDetailsService {

    Optional<Employee> findByUsername(String username);
}
