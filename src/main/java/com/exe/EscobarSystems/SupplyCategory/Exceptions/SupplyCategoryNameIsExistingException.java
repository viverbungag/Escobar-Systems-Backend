package com.exe.EscobarSystems.SupplyCategory.Exceptions;

public class SupplyCategoryNameIsExistingException extends RuntimeException{

    public SupplyCategoryNameIsExistingException(String name){
        super(String.format("The Name %s is already existing", name));
    }
}
