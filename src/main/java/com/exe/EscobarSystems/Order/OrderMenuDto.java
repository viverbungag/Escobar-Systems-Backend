package com.exe.EscobarSystems.Order;

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
public class OrderMenuDto {

    private Long orderMenuId;
    private String orderMenuName;
    private BigDecimal orderMenuPrice;
    private Integer orderMenuQuantity;
    private String orderMenuCategoryName;
    private List<MenuIngredientsDto> ingredients;
    private Integer numberOfServingsLeft;
    private Boolean isActive;
}
