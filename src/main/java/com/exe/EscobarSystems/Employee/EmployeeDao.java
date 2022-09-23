package com.exe.EscobarSystems.Employee;


import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> getEmployeeByFirstAndLastName(String firstName, String lastName);

}
