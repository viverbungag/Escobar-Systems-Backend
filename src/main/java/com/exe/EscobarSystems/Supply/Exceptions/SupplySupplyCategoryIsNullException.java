package com.exe.EscobarSystems.Supply.Exceptions;

public class SupplySupplyCategoryIsNullException extends RuntimeException{

    public SupplySupplyCategoryIsNullException(){
        super("Supply Category should not be empty");
    }
}
