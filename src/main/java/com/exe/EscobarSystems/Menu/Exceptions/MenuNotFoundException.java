package com.exe.EscobarSystems.Menu.Exceptions;

public class MenuNotFoundException extends RuntimeException{

    public MenuNotFoundException(Long id){
        super(String.format("Could not find Menu with id: %s", id));
    }

    public MenuNotFoundException(String name){
        super(String.format("Could not find Menu with name: %s", name));
    }
}
