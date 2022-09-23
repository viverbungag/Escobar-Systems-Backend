package com.exe.EscobarSystems.Menu.Exceptions;

public class MenuPriceIsNotValidException extends RuntimeException{

    public MenuPriceIsNotValidException(){
        super("Menu price has an invalid value");
    }
}
