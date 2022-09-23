package com.exe.EscobarSystems.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeAddress;
    private String employeeContactNumber;
    private LocalDateTime dateEmployed;
    private String employeePositionName;
    private String employeeTypeName;
    private String superiorEmployeeName;
    private Boolean isActive;
}
