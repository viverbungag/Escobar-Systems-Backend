package com.exe.EscobarSystems.EmployeeAttendanceJoin;

import java.util.List;

public interface EmployeeAttendanceJoinDao {


    List<EmployeeAttendanceJoin> getAllAttendance();

    List<EmployeeAttendanceJoin> getAllAttendanceBasedOnEmployeeId(Long employeeId);

    void insertAttendanceJoin(Long employeeId, Long employeeAttendanceId);
}
