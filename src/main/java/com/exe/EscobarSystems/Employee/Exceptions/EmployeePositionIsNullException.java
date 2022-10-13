package com.exe.EscobarSystems.Employee.Exceptions;

public class EmployeePositionIsNullException extends RuntimeException{

    public EmployeePositionIsNullException(){
        super("Position should not be empty");
    }
}
