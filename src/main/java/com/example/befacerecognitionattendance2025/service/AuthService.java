package com.example.befacerecognitionattendance2025.service;

import com.example.befacerecognitionattendance2025.domain.dto.request.LoginRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.EmployeeResponse;
import com.example.befacerecognitionattendance2025.domain.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request) ;

    LoginResponse refreshToken(String refreshTokenRequest);

    EmployeeResponse changePassword(String oldPassword, String newPassword);

    EmployeeResponse verifyPassword(String password);

}
