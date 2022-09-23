package com.exe.EscobarSystems.Transaction.Exceptions;

public class QuantityIsLessThanOneException extends RuntimeException{

    public QuantityIsLessThanOneException(){
        super("Quantity should not be less than 1");
    }
}
