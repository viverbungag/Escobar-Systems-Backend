package com.exe.EscobarSystems.EmployeePosition.Exceptions;

public class EmployeePositionNameIsExistingException extends RuntimeException {

    public EmployeePositionNameIsExistingException(String name){
        super(String.format("The Name %s is already existing", name));
    }
}
