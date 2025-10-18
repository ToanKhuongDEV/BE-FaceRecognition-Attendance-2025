package com.example.befacerecognitionattendance2025.service;


import com.example.befacerecognitionattendance2025.domain.dto.request.TimeFilterRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.PayrollSummaryResponse;

import java.util.List;

public interface PayrollService {

    List<PayrollSummaryResponse> calculateMonthlyPayroll(String departmentId, TimeFilterRequest time);
}
