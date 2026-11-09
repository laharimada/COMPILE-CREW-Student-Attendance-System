package com.compilecrew.attendance.repository;

import com.compilecrew.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);
}