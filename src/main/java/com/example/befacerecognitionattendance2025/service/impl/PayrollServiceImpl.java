package com.example.befacerecognitionattendance2025.service.impl;

import com.example.befacerecognitionattendance2025.domain.dto.request.TimeFilterRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.PayrollSummaryResponse;
import com.example.befacerecognitionattendance2025.repository.AttendanceRepository;
import com.example.befacerecognitionattendance2025.repository.EmployeeRepository;
import com.example.befacerecognitionattendance2025.repository.PayrollRepository;
import com.example.befacerecognitionattendance2025.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;

    @Override
    public List<PayrollSummaryResponse> calculateMonthlyPayroll(String departmentId, TimeFilterRequest time) {
        return payrollRepository.findPayrollByDepartmentAndMonth(time.getMonth(), time.getYear(), departmentId);
    }

}
