package com.example.befacerecognitionattendance2025.util;

import com.example.befacerecognitionattendance2025.constant.ErrorMessage;
import com.example.befacerecognitionattendance2025.exception.InvalidException;

import java.time.LocalDate;

public class ValidateUtil {

    public static void validateCredentials(String username, String password) {
        String USERNAME_REGEX = "^[A-Za-z0-9_.]{5,30}$";
        if (username == null || !username.matches(USERNAME_REGEX)) {
            throw new InvalidException(ErrorMessage.Validation.INVALID_USERNAME);
        }
        String PASSWORD_REGEX ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new InvalidException(ErrorMessage.Validation.INVALID_PASSWORD);
        }
    }

    public static void validateAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        LocalDate minAllowedDate = today.minusYears(18);

        if (dateOfBirth.isAfter(minAllowedDate)) {
            throw new InvalidException(ErrorMessage.Employee.NOT_ENOUGH_AGE);
        }
    }
}
