package com.exe.EscobarSystems.EmployeePosition;

import java.util.List;
import java.util.Optional;

public interface EmployeePositionDao {

    void insertEmployeePosition(String employeePositionName, Boolean isActive);

    Optional<EmployeePosition> getEmployeePositionById(Long employeePositionId);

    Optional<EmployeePosition> getEmployeePositionByName(String employeePositionName);

    void inactivateEmployeePosition(List<String> employeePositionNames);

    void activateEmployeePosition(List<String> employeePositionNames);

    List<EmployeePosition> getAllActiveEmployeePositionsList();

    List<EmployeePosition> getAllActiveEmployeePositions();

    List<EmployeePosition> getAllInactiveEmployeePositions();
}
