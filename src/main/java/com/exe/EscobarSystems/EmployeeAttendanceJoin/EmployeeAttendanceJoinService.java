package com.exe.EscobarSystems.EmployeeAttendanceJoin;

import com.exe.EscobarSystems.Employee.Employee;
import com.exe.EscobarSystems.Employee.EmployeeDao;
import com.exe.EscobarSystems.Employee.Exceptions.EmployeeNotFoundException;
import com.exe.EscobarSystems.EmployeeAttendance.EmployeeAttendance;
import com.exe.EscobarSystems.EmployeeAttendance.EmployeeAttendanceDao;
import com.exe.EscobarSystems.EmployeeAttendance.Exceptions.EmployeeAttendanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeAttendanceJoinService {

    @Autowired
    @Qualifier("employeeAttendanceJoin_mysql")
    EmployeeAttendanceJoinDao employeeAttendanceJoinRepository;

    @Autowired
    @Qualifier("employeeAttendance_mysql")
    EmployeeAttendanceDao employeeAttendanceRepository;

    @Autowired
    @Qualifier("employee_mysql")
    EmployeeDao employeeRepository;

    private EmployeeAttendanceJoinDto convertEntityToDto(EmployeeAttendanceJoin employeeAttendanceJoin){
        return new EmployeeAttendanceJoinDto(
                employeeAttendanceJoin.getEmployeeAttendanceJoinId(),
                String.format("%s, %s", employeeAttendanceJoin.getEmployee().getEmployeeLastName(), employeeAttendanceJoin.getEmployee().getEmployeeFirstName()),
                employeeAttendanceJoin.getEmployeeAttendance().getAttendanceTime(),
                employeeAttendanceJoin.getEmployeeAttendance().getAttendanceType()
        );
    }

    public List<EmployeeAttendanceJoinDto> getAllAttendance(){
        return employeeAttendanceJoinRepository
                .getAllAttendance()
                .stream()
                .map((attendance) -> convertEntityToDto(attendance))
                .collect(Collectors.toList());
    }

    public List<EmployeeAttendanceJoinDto> getAllAttendanceBasedOnEmployeeId(String employeeName){
        String[] employeeSplit = employeeName.split(", ");
        String employeeLastName = employeeSplit[0];
        String employeeFirstName = employeeSplit[1];

        Employee employee = employeeRepository
                .getEmployeeByFirstAndLastName(employeeFirstName, employeeLastName)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(employeeFirstName, employeeLastName));

        return employeeAttendanceJoinRepository
                .getAllAttendanceBasedOnEmployeeId(employee.getEmployeeId())
                .stream()
                .map((attendance) -> convertEntityToDto(attendance))
                .collect(Collectors.toList());
    }

    public void addAttendance(EmployeeAttendanceJoinDto employeeAttendanceJoinDto){
        String employeeName = employeeAttendanceJoinDto.getEmployeeName();

        String[] employeeSplit = employeeName.split(", ");
        String employeeLastName = employeeSplit[0];
        String employeeFirstName = employeeSplit[1];

        LocalDateTime attendanceTime = employeeAttendanceJoinDto.getAttendanceTime();
        String attendanceType = employeeAttendanceJoinDto.getAttendanceType().toString();

        employeeAttendanceRepository
                .insertAttendance(attendanceTime, attendanceType);

        EmployeeAttendance employeeAttendance = employeeAttendanceRepository
                .getEmployeeAttendanceByAttendanceTime(attendanceTime)
                .orElseThrow(() -> new EmployeeAttendanceNotFoundException(attendanceTime));

        Employee employee = employeeRepository
                .getEmployeeByFirstAndLastName(employeeFirstName, employeeLastName)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeFirstName, employeeLastName));

        employeeAttendanceJoinRepository
                .insertAttendanceJoin(employee.getEmployeeId(), employeeAttendance.getEmployeeAttendanceId());

    }
}
