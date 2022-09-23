package com.exe.EscobarSystems.Transaction.Exceptions;

public class PricePerUnitIsLessThanZeroException extends RuntimeException{

    public PricePerUnitIsLessThanZeroException(){
        super("Price per Unit should not be less than 0");
    }
}
