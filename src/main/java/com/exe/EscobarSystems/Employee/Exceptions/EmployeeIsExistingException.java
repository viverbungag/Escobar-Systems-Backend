package com.exe.EscobarSystems.Employee.Exceptions;

public class EmployeeIsExistingException extends RuntimeException{

    public EmployeeIsExistingException(String firstName, String lastName){
        super(String.format("This name %s, %s is already existing", lastName, firstName));
    }
}
