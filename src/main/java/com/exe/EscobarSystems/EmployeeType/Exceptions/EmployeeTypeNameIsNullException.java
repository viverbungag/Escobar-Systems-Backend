package com.exe.EscobarSystems.EmployeeType.Exceptions;

public class EmployeeTypeNameIsNullException extends RuntimeException {

    public EmployeeTypeNameIsNullException(){
        super("Type Name should not be empty");
    }
}
