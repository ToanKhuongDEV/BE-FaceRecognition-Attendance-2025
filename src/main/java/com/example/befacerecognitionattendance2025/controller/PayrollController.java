package com.example.befacerecognitionattendance2025.controller;

import com.example.befacerecognitionattendance2025.base.RestApiV1;
import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import com.example.befacerecognitionattendance2025.constant.UrlConstant;
import com.example.befacerecognitionattendance2025.domain.dto.request.TimeFilterRequest;
import com.example.befacerecognitionattendance2025.service.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollService payrollService;

    @GetMapping(UrlConstant.Payroll.GET_BY_DEPARTMENT)
    public ResponseEntity<RestData<?>> calculateMonthlyPayroll (
            @PathVariable String departmentId,
            @Valid @ModelAttribute TimeFilterRequest time
    ) {
        return VsResponseUtil.success(payrollService.calculateMonthlyPayroll(departmentId,time));
    }
}
