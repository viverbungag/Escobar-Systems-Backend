package com.exe.EscobarSystems.MenuCategory.Exceptions;

public class MenuCategoryNameIsExistingException extends RuntimeException{

    public MenuCategoryNameIsExistingException(String name){
        super(String.format("The Name %s is already existing", name));
    }
}
