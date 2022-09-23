package com.exe.EscobarSystems.EmployeeType;

import java.util.List;
import java.util.Optional;

public interface EmployeeTypeDao {

    void insertEmployeeType(String employeeTypeName, Boolean isActive);

    Optional<EmployeeType> getEmployeeTypeById(Long employeeTypeId);

    Optional<EmployeeType> getEmployeeTypeByName(String employeeTypeName);

    void inactivateEmployeeType(List<String> employeeTypeNames);

    void activateEmployeeType(List<String> employeeTypeNames);

    List<EmployeeType> getAllActiveEmployeeTypesList();

    List<EmployeeType> getAllActiveEmployeeTypes();

    List<EmployeeType> getAllInactiveEmployeeTypes();
}
