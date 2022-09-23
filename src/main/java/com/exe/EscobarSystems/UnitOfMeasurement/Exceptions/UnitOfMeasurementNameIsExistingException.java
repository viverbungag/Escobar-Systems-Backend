package com.exe.EscobarSystems.UnitOfMeasurement.Exceptions;

public class UnitOfMeasurementNameIsExistingException extends RuntimeException{

    public UnitOfMeasurementNameIsExistingException(String name){
        super(String.format("This Name %s is already existing", name));
    }
}
