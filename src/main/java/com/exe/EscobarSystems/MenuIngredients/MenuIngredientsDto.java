package com.exe.EscobarSystems.MenuIngredients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuIngredientsDto {

    private Long menuIngredientsId;
    private String supplyName;
    private Double quantity;
    private String unitOfMeasurementName;


}
