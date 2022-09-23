package com.exe.EscobarSystems.EmployeePosition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class EmployeePositionDto {

    private Long employeePositionId;
    private String employeePositionName;
    private Boolean isActive;
}
