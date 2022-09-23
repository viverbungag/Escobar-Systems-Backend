package com.exe.EscobarSystems.Menu.Exceptions;

public class MenuNameIsNullException extends RuntimeException{

    public MenuNameIsNullException(){
        super("Menu name should not be empty");
    }
}
