package com.example.befacerecognitionattendance2025.controller;

import com.example.befacerecognitionattendance2025.base.RestApiV1;
import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import com.example.befacerecognitionattendance2025.constant.UrlConstant;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateEmployeeRequest;
import com.example.befacerecognitionattendance2025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(
            value = UrlConstant.Employee.COMMON,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RestData<?>> createEmployee(CreateEmployeeRequest request, MultipartFile imageFile) {
        return VsResponseUtil.success(service.createManager(request, imageFile));
    }



}
