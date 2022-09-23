package com.exe.EscobarSystems.MenuCategory;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "menu_category")
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_category_id")
    private Long menuCategoryId;

    @NonNull
    @Column(name = "menu_category_name")
    private String menuCategoryName;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
