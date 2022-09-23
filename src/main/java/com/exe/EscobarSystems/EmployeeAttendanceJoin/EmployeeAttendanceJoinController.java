package com.exe.EscobarSystems.EmployeeAttendanceJoin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/attendance")
public class EmployeeAttendanceJoinController {

    @Autowired
    EmployeeAttendanceJoinService employeeAttendanceJoinService;

    @GetMapping
    public List<EmployeeAttendanceJoinDto> getAllAttendance(){
        return employeeAttendanceJoinService.getAllAttendance();
    }

    @GetMapping("/{employeeName}")
    public List<EmployeeAttendanceJoinDto> getAllAttendanceBasedOnEmployeeId(@PathVariable String employeeName){
        return employeeAttendanceJoinService.getAllAttendanceBasedOnEmployeeId(employeeName);
    }

    @PostMapping("/add")
    public void addAttendance(@RequestBody EmployeeAttendanceJoinDto employeeAttendanceJoinDto){
        employeeAttendanceJoinService.addAttendance(employeeAttendanceJoinDto);
    }
}
