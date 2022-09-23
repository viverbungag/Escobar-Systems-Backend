package com.exe.EscobarSystems.Menu;


import com.exe.EscobarSystems.MenuIngredients.MenuIngredientsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDto {

    private Long menuId;
    private String menuName;
    private BigDecimal menuPrice;
    private String menuCategoryName;
    private List<MenuIngredientsDto> ingredients;
    private Integer numberOfServingsLeft;
    private Boolean isActive;
}
