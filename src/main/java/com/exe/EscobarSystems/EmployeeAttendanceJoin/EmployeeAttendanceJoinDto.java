package com.exe.EscobarSystems.EmployeeAttendanceJoin;

import com.exe.EscobarSystems.EmployeeAttendance.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeAttendanceJoinDto {

    private Long employeeAttendanceJoinId;
    private String employeeName;
    private LocalDateTime attendanceTime;
    private AttendanceType attendanceType;
}
