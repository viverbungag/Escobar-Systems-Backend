package com.exe.EscobarSystems.EmployeeAttendance.Exceptions;

import java.time.LocalDateTime;

public class EmployeeAttendanceNotFoundException extends RuntimeException{

    public EmployeeAttendanceNotFoundException(LocalDateTime attendanceTime){
        super(String.format("Could not find Employee Attendance with time: %s",attendanceTime));
    }
}
