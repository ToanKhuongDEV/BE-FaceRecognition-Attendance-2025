package com.example.befacerecognitionattendance2025.domain.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRequest {

    private String employeeId;
    private Integer month;
    private Integer year;
    private Double totalHours;
    private Double bonus;
    private Double deduction;
}
