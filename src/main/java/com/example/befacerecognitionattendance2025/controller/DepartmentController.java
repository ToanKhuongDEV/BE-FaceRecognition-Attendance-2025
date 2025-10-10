package com.example.befacerecognitionattendance2025.controller;

import com.example.befacerecognitionattendance2025.base.RestApiV1;
import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import com.example.befacerecognitionattendance2025.constant.UrlConstant;
import com.example.befacerecognitionattendance2025.domain.dto.request.AddEmployeesRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateDepartmentRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.UpdateDepartmentRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.DepartmentResponse;
import com.example.befacerecognitionattendance2025.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestApiV1
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(UrlConstant.Department.COMMON)
    public ResponseEntity<RestData<?>> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request) {
        DepartmentResponse response = departmentService.createDepartment(request);
        return VsResponseUtil.success(response);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping(UrlConstant.Department.ID)
    public ResponseEntity<RestData<?>> updateDepartment(
            @PathVariable String id,
            @RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse response = departmentService.updateDepartment(id, request);
        return VsResponseUtil.success(response);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping(UrlConstant.Department.ID)
    public ResponseEntity<RestData<?>> deleteDepartment(@PathVariable String id) {
        DepartmentResponse response = departmentService.deleteDepartment(id);
        return VsResponseUtil.success(response);
    }

    @GetMapping(UrlConstant.Department.ID)
    public ResponseEntity<RestData<?>> getDepartmentById(@PathVariable String id) {
        DepartmentResponse response = departmentService.findDepartmentById(id);
        return VsResponseUtil.success(response);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(UrlConstant.Department.ADD_EMPLOYEE)
    public ResponseEntity<RestData<?>> addEmployeeToDepartment(@PathVariable String id, @RequestBody @Valid AddEmployeesRequest request) {
        DepartmentResponse response = departmentService.addEmployeesToDepartment(id, request);
        return VsResponseUtil.success(response);
    }
}
