package com.exe.EscobarSystems.Supply.Exceptions;

public class SupplyNameIsNullException extends RuntimeException {

    public SupplyNameIsNullException(){
        super("Supply name should not be empty");
    }
}
