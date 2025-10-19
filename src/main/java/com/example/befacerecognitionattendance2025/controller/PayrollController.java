package com.example.befacerecognitionattendance2025.controller;

import com.example.befacerecognitionattendance2025.base.RestApiV1;
import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import com.example.befacerecognitionattendance2025.constant.UrlConstant;
import com.example.befacerecognitionattendance2025.domain.dto.request.PayrollEntryRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.TimeFilterRequest;
import com.example.befacerecognitionattendance2025.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollService payrollService;

    @Operation(summary = "lấy bảng lương theo tháng hoặc năm của 1 phòng ban theo  ", description = "yêu cầu quyền manager")
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping(UrlConstant.Payroll.GET_BY_DEPARTMENT)
    public ResponseEntity<RestData<?>> getMonthlyPayroll (
            @PathVariable String departmentId,
            @Valid @ModelAttribute TimeFilterRequest time
    ) {
        return VsResponseUtil.success(payrollService.getPayrollByDepartmentId(departmentId,time));
    }

    @Operation(summary = "tạo bảng lương cho tất cả các nhân viên", description = "yêu cầu quyền manager")
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(UrlConstant.Payroll.COMMON)
    public ResponseEntity<RestData<?>> createMonthlyPayroll (
            @Valid @ModelAttribute TimeFilterRequest time
    ) {
        return VsResponseUtil.success(HttpStatus.CREATED,payrollService.createPayroll(time));
    }

    @Operation(summary = "lấy bảng lương của bản thân theo tháng")
    @GetMapping(UrlConstant.Payroll.ME)
    public ResponseEntity<RestData<?>> getMyPayroll (
            @Valid @ModelAttribute TimeFilterRequest time
    ) {
        return VsResponseUtil.success(payrollService.getMyPayroll(time));
    }

    @Operation(summary = "cập nhật thưởng hoặc phạt cho nhân viên", description = "yêu cầu quyền manager")
    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping(UrlConstant.Payroll.UPDATE_BONUS_DEDUCTION)
    public ResponseEntity<RestData<?>> updateBonusDeduction(
            @PathVariable String employeeId,
            @RequestBody @Valid PayrollEntryRequest request,
            @Valid @ModelAttribute TimeFilterRequest time
    ){
        return VsResponseUtil.success(payrollService.updateBonusDeduction(employeeId,request,time));
    }
}
