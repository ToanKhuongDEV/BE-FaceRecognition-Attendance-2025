package com.example.befacerecognitionattendance2025.domain.mapper;

import com.example.befacerecognitionattendance2025.domain.dto.request.PayrollRequest;
import com.example.befacerecognitionattendance2025.domain.entity.Payroll;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollMapper {

    Payroll toEntity(PayrollRequest payrollRequest);
}
