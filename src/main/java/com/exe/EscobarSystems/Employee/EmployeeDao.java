package com.exe.EscobarSystems.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> getAllActiveEmployees();

    List<Employee> getAllInactiveEmployees();

    void inactivateEmployees(List<Long> employeeIds);

    void activateEmployees(List<Long> employeeIds);

    void insertEmployees(String employeeFirstName,
                         String employeeLastName,
                         String employeeAddress,
                         String employeeContactNumber,
                         LocalDateTime dateEmployed,
                         Long employeePositionId,
                         Long employeeTypeId,
                         Long superiorEmployee,
                         Boolean isActive);

    Optional<Employee> getEmployeeById(Long employeeId);

    Optional<Employee> getEmployeeByFirstAndLastName(String firstName,
                                                     String lastName);



}
