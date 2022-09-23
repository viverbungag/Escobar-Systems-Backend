package com.exe.EscobarSystems.MenuIngredients;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("menuIngredients_mysql")
public interface MenuIngredientsMySqlRepository extends MenuIngredientsDao, JpaRepository<MenuIngredients, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_ingredients_id = :menuIngredientsId",
            nativeQuery = true)
    Optional<MenuIngredients> getMenuIngredientsById(@Param("menuIngredientsId") Long menuIngredientsId);

    @Query(value = "SELECT * FROM #{#entityName} menu_ingredients " +
            "INNER JOIN menu AS menu ON menu_ingredients.menu_id = menu.menu_id " +
            "WHERE menu_name = :menuName",
            nativeQuery = true)
    Optional<MenuIngredients> getMenuIngredientsByName(@Param("menuName") String menuName);

    @Modifying
    @Query(value = "DELETE FROM #{#entityName} WHERE menu_id = :menuId",
            nativeQuery = true)
    void deleteAllMenuIngredientsByMenuId(@Param("menuId") Long menuId);
}
