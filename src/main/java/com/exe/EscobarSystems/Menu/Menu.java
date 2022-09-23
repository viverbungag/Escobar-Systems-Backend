package com.exe.EscobarSystems.Menu;

import com.exe.EscobarSystems.MenuCategory.MenuCategory;
import com.exe.EscobarSystems.MenuIngredients.MenuIngredients;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @NonNull
    @Column(name = "menu_name")
    private String menuName;

    @NonNull
    @Column(name = "menu_price")
    private BigDecimal menuPrice;

    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<MenuIngredients> menuIngredients;

    @NonNull
    @Transient
    @Column(name = "number_of_servings_left")
    private Integer numberOfServingsLeft;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
