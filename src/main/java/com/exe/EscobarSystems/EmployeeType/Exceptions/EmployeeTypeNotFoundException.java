package com.exe.EscobarSystems.EmployeeType.Exceptions;

public class EmployeeTypeNotFoundException extends RuntimeException {

    public EmployeeTypeNotFoundException(Long id){
        super(String.format("Could not find Employee Type with id: %s", id));
    }

    public EmployeeTypeNotFoundException(String name){
        super(String.format("Could not find Employee Type with name: %s", name));
    }
}
