package com.exe.EscobarSystems.FoodOrder;

import com.exe.EscobarSystems.Menu.Menu;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "food_order")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "food_order_id")
    private Long foodOrderId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @NonNull
    @Column(name = "menu_quantity")
    private Integer menuQuantity;

}
