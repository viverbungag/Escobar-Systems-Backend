package com.exe.EscobarSystems.UnitOfMeasurement.Exceptions;

public class UnitOfMeasurementNotFoundException extends RuntimeException{

    public UnitOfMeasurementNotFoundException(Long id){
        super(String.format("Could not find Supplier with id: %s", id));
    }

    public UnitOfMeasurementNotFoundException(String name){
        super(String.format("Could not find Supplier with name: %s", name));
    }
}
