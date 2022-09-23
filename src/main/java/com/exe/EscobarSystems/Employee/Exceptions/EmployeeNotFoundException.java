package com.exe.EscobarSystems.Employee.Exceptions;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long id){
        super(String.format("Could not find Employee with id: %s", id));
    }

    public EmployeeNotFoundException(String firstName, String lastName){
        super(String.format("Could not find Employee with first name: %s and last name: %s", firstName, lastName));
    }
}
