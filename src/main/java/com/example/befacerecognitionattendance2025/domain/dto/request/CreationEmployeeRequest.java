package com.example.befacerecognitionattendance2025.domain.dto.request;

import com.example.befacerecognitionattendance2025.constant.ErrorMessage;
import com.example.befacerecognitionattendance2025.constant.Gender;
import com.example.befacerecognitionattendance2025.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreationEmployeeRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String username;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String password;

    private String fullName;

    private Gender gender;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private Role role;

    private LocalDate dateBirth;
    private String phoneNumber;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Email(message = ErrorMessage.Validation.INVALID_EMAIL)
    private String email;

}
