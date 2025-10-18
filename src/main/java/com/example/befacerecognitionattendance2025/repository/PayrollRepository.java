package com.example.befacerecognitionattendance2025.repository;

import com.example.befacerecognitionattendance2025.domain.dto.response.PayrollSummaryResponse;
import com.example.befacerecognitionattendance2025.domain.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, String> {

    @Query("""
           SELECT new com.example.befacerecognitionattendance2025.domain.dto.response.PayrollSummaryResponse(
               p.employee.employeeCode,
               p.employee.fullName,
               p.employee.department.name,
               p.finalSalary
           )
           FROM Payroll p
           WHERE p.month = :month AND p.year = :year
             AND p.employee.department.id = :departmentId
           """)
    List<PayrollSummaryResponse> findPayrollByDepartmentAndMonth(
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("departmentId") String departmentId
    );
}
