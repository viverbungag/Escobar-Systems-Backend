package com.exe.EscobarSystems.EmployeeAttendance;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmployeeAttendanceDao {

    void insertAttendance(LocalDateTime attendanceTime,
                          String attendanceType);

    Optional<EmployeeAttendance> getEmployeeAttendanceByAttendanceTime(LocalDateTime attendanceTime);
}
