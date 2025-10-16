package com.example.befacerecognitionattendance2025.repository;

import com.example.befacerecognitionattendance2025.domain.entity.Attendance;
import com.example.befacerecognitionattendance2025.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

    /**
     * Truy vấn động: lọc theo employeeId, và tùy chọn theo day / month / year.
     * - Nếu chỉ có year → lọc theo năm.
     * - Nếu có month + year → lọc theo tháng.
     * - Nếu có day + month + year → lọc theo ngày cụ thể.
     */
    @Query("""
        SELECT a
        FROM Attendance a
        WHERE a.employee.id = :employeeId
          AND (:year IS NULL OR FUNCTION('YEAR', a.workDate) = :year)
          AND (:month IS NULL OR FUNCTION('MONTH', a.workDate) = :month)
          AND (:day IS NULL OR FUNCTION('DAY', a.workDate) = :day)
        ORDER BY a.workDate ASC
    """)
    List<Attendance> findAttendanceDynamic(
            @Param("employeeId") String employeeId,
            @Param("day") Integer day,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    @Query("""
        SELECT a
        FROM Attendance a
        WHERE a.employee.id = :employeeId
          AND a.checkOutTime IS NULL
        ORDER BY a.checkInTime DESC
        """)
    Optional<Attendance> findLatestUnfinished(@Param("employeeId") String employeeId);


}
