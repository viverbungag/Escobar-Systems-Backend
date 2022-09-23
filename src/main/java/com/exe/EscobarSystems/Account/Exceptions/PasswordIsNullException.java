package com.exe.EscobarSystems.Account.Exceptions;

public class PasswordIsNullException extends RuntimeException {

    public PasswordIsNullException(){
        super("Password should not be empty");
    }
}
