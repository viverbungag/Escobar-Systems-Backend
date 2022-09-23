package com.exe.EscobarSystems.EmployeeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeTypeDto {

    private Long employeeTypeId;
    private String employeeTypeName;
    private Boolean isActive;
}
