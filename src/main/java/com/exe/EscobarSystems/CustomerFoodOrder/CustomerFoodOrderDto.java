package com.exe.EscobarSystems.CustomerFoodOrder;

import com.exe.EscobarSystems.FoodOrder.FoodOrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerFoodOrderDto {

    private Long customerFoodOrderId;
    private FoodOrderDto foodOrder;
}
