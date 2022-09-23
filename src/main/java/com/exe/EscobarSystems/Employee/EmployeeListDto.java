package com.exe.EscobarSystems.Employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmployeeListDto {

    List<EmployeeDto> employeeListDto;
}
