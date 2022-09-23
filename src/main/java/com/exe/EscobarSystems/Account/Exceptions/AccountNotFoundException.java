package com.exe.EscobarSystems.Account.Exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(Long id){
        super(String.format("Could not find Account with id: %s", id));
    }

    public AccountNotFoundException(String name){
        super(String.format("Could not find Account with name: %s", name));
    }
}
