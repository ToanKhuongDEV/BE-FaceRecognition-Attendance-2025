package com.example.befacerecognitionattendance2025.service.impl;

import com.example.befacerecognitionattendance2025.constant.ErrorMessage;
import com.example.befacerecognitionattendance2025.constant.Role;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.UpdateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.EmployeeResponse;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import com.example.befacerecognitionattendance2025.domain.mapper.EmployeeMapper;
import com.example.befacerecognitionattendance2025.exception.DuplicateResourceException;
import com.example.befacerecognitionattendance2025.exception.InvalidException;
import com.example.befacerecognitionattendance2025.repository.EmployeeRepository;
import com.example.befacerecognitionattendance2025.service.EmployeeService;
import com.example.befacerecognitionattendance2025.util.UploadFileUtil;
import com.example.befacerecognitionattendance2025.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UploadFileUtil uploadFileUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request, MultipartFile imageFile) {

        if( employeeRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateResourceException(ErrorMessage.Employee.ERR_EMAIL_EXISTS);
        }

        ValidateUtil.validateAge(request.getDateBirth());
        ValidateUtil.validateCredentials(request.getUsername(), request.getPassword());
        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(Role.STAFF);
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            employee.setAvatar(imageUrl);
        }
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employeeRepository.save(employee);
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse createManager(CreateEmployeeRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public EmployeeResponse deleteEmployee(String id) {
        return null;
    }

    @Override
    public EmployeeResponse updateEmployee(String id, UpdateEmployeeRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public EmployeeResponse getAllEmployee() {
        return null;
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {
        return null;
    }

}
