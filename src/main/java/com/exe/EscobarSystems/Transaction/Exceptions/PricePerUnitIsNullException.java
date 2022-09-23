package com.exe.EscobarSystems.Transaction.Exceptions;

public class PricePerUnitIsNullException extends RuntimeException{

    public PricePerUnitIsNullException(){
        super("Price per Unit field should not be empty");
    }
}
