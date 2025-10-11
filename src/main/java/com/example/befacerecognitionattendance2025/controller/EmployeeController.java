package com.example.befacerecognitionattendance2025.controller;

import com.example.befacerecognitionattendance2025.base.RestApiV1;
import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import com.example.befacerecognitionattendance2025.constant.UrlConstant;
import com.example.befacerecognitionattendance2025.domain.dto.request.ChangePasswordRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.UpdateEmployeeRequest;
import com.example.befacerecognitionattendance2025.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(
            value = UrlConstant.Employee.COMMON,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RestData<?>> createEmployee(
            @Valid @RequestPart("data")  CreateEmployeeRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(service.createEmployee(request, imageFile));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(
            value = UrlConstant.Employee.CREATE_MANAGER,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RestData<?>> createManager(
            @Valid @RequestPart("data") CreateEmployeeRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        return VsResponseUtil.success(service.createManager(request, imageFile));
    }

    @PostMapping(UrlConstant.Employee.CHANGE_PASSWORD)
    public ResponseEntity<RestData<?>> changePassword(@Valid @RequestBody ChangePasswordRequest request ) {
        return VsResponseUtil.success(service.changePassword(request));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PatchMapping(
            value = UrlConstant.Employee.ID,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RestData<?>> updateEmployee(
            @PathVariable("id") String id,
            @Valid @RequestPart("data") UpdateEmployeeRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        return VsResponseUtil.success(service.updateEmployee(id, request, imageFile));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping(UrlConstant.Employee.ID)
    public ResponseEntity<RestData<?>> deleteEmployee(@PathVariable("id") String id) {
        return VsResponseUtil.success(service.deleteEmployee(id));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping(UrlConstant.Employee.COMMON)
    public ResponseEntity<RestData<?>> getAllEmployees() {
        return VsResponseUtil.success(service.getAllEmployee());
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping(UrlConstant.Employee.ID)
    public ResponseEntity<RestData<?>> getEmployeeById(@PathVariable("id") String id) {
        return VsResponseUtil.success(service.getEmployeeById(id));
    }

}
