package com.exe.EscobarSystems.Employee.Exceptions;

public class EmployeeTypeIsNullException extends RuntimeException{

    public EmployeeTypeIsNullException(){
        super("Type should not be empty");
    }
}
