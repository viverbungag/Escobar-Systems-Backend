package com.exe.EscobarSystems.Account.Exceptions;

public class UsernameIsNullException extends RuntimeException {

    public UsernameIsNullException(){
        super("Username should not be empty");
    }
}
