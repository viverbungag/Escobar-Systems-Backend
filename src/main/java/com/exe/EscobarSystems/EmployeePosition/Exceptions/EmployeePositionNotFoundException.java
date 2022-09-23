package com.exe.EscobarSystems.EmployeePosition.Exceptions;

public class EmployeePositionNotFoundException extends RuntimeException {

    public EmployeePositionNotFoundException(Long id){
        super(String.format("Could not find Employee Position with id: %s", id));
    }

    public EmployeePositionNotFoundException(String name){
        super(String.format("Could not find Employee Position with name: %s", name));
    }

}
