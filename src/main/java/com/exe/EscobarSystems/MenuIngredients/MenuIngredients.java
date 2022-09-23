package com.exe.EscobarSystems.MenuIngredients;



import com.exe.EscobarSystems.Menu.Menu;
import com.exe.EscobarSystems.Supply.Supply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity(name = "menu_ingredients")
public class MenuIngredients {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_ingredients_id")
    private Long menuIngredientsId;

    @NonNull
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @NonNull
    @Column(name = "quantity")
    private Double quantity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

}
