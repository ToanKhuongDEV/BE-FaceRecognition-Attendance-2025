package com.example.befacerecognitionattendance2025.service.impl;

import com.example.befacerecognitionattendance2025.domain.dto.request.AttendanceFilterRequest;
import com.example.befacerecognitionattendance2025.domain.dto.response.AttendanceSummaryDTO;
import com.example.befacerecognitionattendance2025.domain.entity.Attendance;
import com.example.befacerecognitionattendance2025.domain.mapper.AttendanceMapper;
import com.example.befacerecognitionattendance2025.repository.AttendanceRepository;
import com.example.befacerecognitionattendance2025.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public List<AttendanceSummaryDTO> getTotalWorkingHoursByFilter(String employeeId, AttendanceFilterRequest request) {
        List<Attendance> attendances = attendanceRepository.findAttendanceDynamic(employeeId, request.getYear(), request.getMonth(), request.getDay());
        return attendanceMapper.toSummaryDTOList(attendances);
    }

    @Override
    public AttendanceSummaryDTO recordFaceAttendance(MultipartFile faceImage) {
        return null;
    }
}
