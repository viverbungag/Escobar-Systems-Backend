package com.exe.EscobarSystems.FoodOrder;

import com.exe.EscobarSystems.Menu.MenuDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class FoodOrderDto {

    private Long foodOrderId;
    private MenuDto menu;
    private Integer menuQuantity;
}
