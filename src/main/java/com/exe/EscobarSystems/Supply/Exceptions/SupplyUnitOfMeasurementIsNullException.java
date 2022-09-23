package com.exe.EscobarSystems.Supply.Exceptions;

public class SupplyUnitOfMeasurementIsNullException extends RuntimeException{

    public SupplyUnitOfMeasurementIsNullException(){
        super("Unit of Measurement should not be empty");
    }
}
