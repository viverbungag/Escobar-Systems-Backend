package com.exe.EscobarSystems.MenuCategory.Exceptions;

public class MenuCategoryNotFoundException extends RuntimeException{

    public MenuCategoryNotFoundException(Long id){
        super(String.format("Could not find Menu Category with id: %s", id));
    }

    public MenuCategoryNotFoundException(String name){
        super(String.format("Could not find Menu Category with name: %s", name));
    }
}
