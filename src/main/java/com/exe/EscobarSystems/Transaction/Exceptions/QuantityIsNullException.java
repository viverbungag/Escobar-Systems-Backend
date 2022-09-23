package com.exe.EscobarSystems.Transaction.Exceptions;

public class QuantityIsNullException extends RuntimeException{

    public QuantityIsNullException(){
        super("Quantity field should not be empty");
    }
}
