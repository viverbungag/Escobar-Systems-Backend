package com.exe.EscobarSystems.MenuIngredients.Exceptions;

public class MenuIngredientsNotFoundException extends RuntimeException{

    public MenuIngredientsNotFoundException(Long id){
        super(String.format("Could not find Menu Ingredient with id: %s", id));
    }

    public MenuIngredientsNotFoundException(String name){
        super(String.format("Could not find Menu Ingredient with name: %s", name));
    }
}
