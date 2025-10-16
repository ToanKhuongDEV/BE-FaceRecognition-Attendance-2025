package com.example.befacerecognitionattendance2025.domain.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceSummaryDTO {
    private String employeeId;
    private LocalDate workDate;
    private Double totalHours;
}
