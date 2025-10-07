package com.example.befacerecognitionattendance2025.service.impl;

import com.example.befacerecognitionattendance2025.constant.ErrorMessage;
import com.example.befacerecognitionattendance2025.constant.Role;
import com.example.befacerecognitionattendance2025.domain.dto.request.ChangePasswordRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.UpdateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.EmployeeResponse;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import com.example.befacerecognitionattendance2025.domain.mapper.EmployeeMapper;
import com.example.befacerecognitionattendance2025.exception.DuplicateResourceException;
import com.example.befacerecognitionattendance2025.exception.InvalidException;
import com.example.befacerecognitionattendance2025.exception.UnauthorizedException;
import com.example.befacerecognitionattendance2025.repository.EmployeeRepository;
import com.example.befacerecognitionattendance2025.security.UserPrincipal;
import com.example.befacerecognitionattendance2025.service.EmployeeService;
import com.example.befacerecognitionattendance2025.util.UploadFileUtil;
import com.example.befacerecognitionattendance2025.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Transactional
    public EmployeeResponse changePassword(ChangePasswordRequest request) {
        // 1. Lấy user hiện tại từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 2. Tìm employee từ DB
        var employee = employeeRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new InvalidException(ErrorMessage.Employee.ERR_NOT_FOUND));

        // 3. Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldPassword(), employee.getPassword())) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
        }

        if(!this.validatePassword(request.getNewPassword())){
            throw new InvalidException(ErrorMessage.Validation.ERR_INVALID_PASSWORD);
        }
        // 4. Encode mật khẩu mới và lưu lại
        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
        employeeRepository.save(employee);

        // 5. Trả về EmployeeResponse
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

    private Boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$";
        return password.matches(regex);
    }

}
