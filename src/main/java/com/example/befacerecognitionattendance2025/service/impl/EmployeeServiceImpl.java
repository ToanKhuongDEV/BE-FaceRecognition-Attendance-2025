package com.example.befacerecognitionattendance2025.service.impl;

import com.example.befacerecognitionattendance2025.constant.ErrorMessage;
import com.example.befacerecognitionattendance2025.constant.Role;
import com.example.befacerecognitionattendance2025.domain.dto.request.ChangePasswordRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.CreateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.request.UpdateEmployeeRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.EmployeeResponse;
import com.example.befacerecognitionattendance2025.domain.entity.Department;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import com.example.befacerecognitionattendance2025.domain.mapper.EmployeeMapper;
import com.example.befacerecognitionattendance2025.exception.DuplicateResourceException;
import com.example.befacerecognitionattendance2025.exception.InvalidException;
import com.example.befacerecognitionattendance2025.exception.NotFoundException;
import com.example.befacerecognitionattendance2025.exception.UnauthorizedException;
import com.example.befacerecognitionattendance2025.repository.DepartmentRepository;
import com.example.befacerecognitionattendance2025.repository.EmployeeRepository;
import com.example.befacerecognitionattendance2025.security.UserPrincipal;
import com.example.befacerecognitionattendance2025.service.AuthService;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final UploadFileUtil uploadFileUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request, MultipartFile imageFile) {

        if( employeeRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateResourceException(ErrorMessage.Employee.ERR_EMAIL_EXISTS);
        }
        if( employeeRepository.findByUsername(request.getUsername()).isPresent()){
            throw new DuplicateResourceException(ErrorMessage.Employee.ERR_USERNAME_EXISTS);
        }

        ValidateUtil.validateAge(request.getDateBirth());
        ValidateUtil.validateCredentials(request.getUsername(), request.getPassword());
        Employee employee = employeeMapper.toEntity(request);
        Department  department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow( () -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND));
        employee.setDepartment(department);
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
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));

        // 3. Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldPassword(), employee.getPassword())) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
            }

        if (!ValidateUtil.validatePassword(request.getNewPassword())) {
            throw new InvalidException(ErrorMessage.Validation.ERR_INVALID_PASSWORD);
        }
        // 4. Encode mật khẩu mới và lưu lại
        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
        employeeRepository.save(employee);

        // 5. Trả về EmployeeResponse
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse createManager(CreateEmployeeRequest request, MultipartFile file) {
        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException(ErrorMessage.Employee.ERR_EMAIL_EXISTS);
        }
        if (employeeRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException(ErrorMessage.Employee.ERR_USERNAME_EXISTS);
        }

        ValidateUtil.validateAge(request.getDateBirth());
        ValidateUtil.validateCredentials(request.getUsername(), request.getPassword());

        Employee manager = employeeMapper.toEntity(request);
        Department  department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow( () -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND));
        manager.setDepartment(department);
        manager.setRole(Role.MANAGER);

        if (file != null && !file.isEmpty()) {
            UploadFileUtil.validateIsImage(file);
            String imageUrl = uploadFileUtil.uploadImage(file);
            manager.setAvatar(imageUrl);
        }

        manager.setPassword(passwordEncoder.encode(request.getPassword()));
        employeeRepository.save(manager);
        return employeeMapper.toResponse(manager);
    }

    @Override
    @Transactional
    public EmployeeResponse deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));

        employeeRepository.delete(employee);
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(String id, UpdateEmployeeRequest request, MultipartFile file) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));

        if (request.getEmail() != null && !request.getEmail().equals(employee.getEmail())) {
            if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new DuplicateResourceException(ErrorMessage.Employee.ERR_EMAIL_EXISTS);
            }
        }

       employeeMapper.updateEmployee(request,employee);
        if(request.getDepartmentId() != null){
            Department  department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow( () -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND));
            employee.setDepartment(department);
        }
        if (file != null && !file.isEmpty()) {
            UploadFileUtil.validateIsImage(file);
            String imageUrl = uploadFileUtil.uploadImage(file);
            employee.setAvatar(imageUrl);
        }

        employeeRepository.save(employee);
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateMyProfile(UpdateEmployeeRequest request, MultipartFile file) {
        Employee employee = employeeRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));

        if (request.getEmail() != null && !request.getEmail().equals(employee.getEmail())) {
            if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new DuplicateResourceException(ErrorMessage.Employee.ERR_EMAIL_EXISTS);
            }
        }

        employeeMapper.updateEmployee(request,employee);

        if(request.getDepartmentId() != null){
            Department  department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow( () -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND));
            employee.setDepartment(department);
        }
        if (file != null && !file.isEmpty()) {
            UploadFileUtil.validateIsImage(file);
            String imageUrl = uploadFileUtil.uploadImage(file);
            employee.setAvatar(imageUrl);
        }

        employeeRepository.save(employee);
        return employeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        return employeeMapper.toResponseList(employeeRepository.findAll());
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse getMe() {
        Employee employee = employeeRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND));
        return  employeeMapper.toResponse(employee);
    }


}
