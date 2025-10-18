package com.example.befacerecognitionattendance2025.domain.dto.response;

public record PayrollSummaryResponse(
        Integer employeeCode,
        String employeeName,
        String departmentName,
        Double finalSalary
) {}