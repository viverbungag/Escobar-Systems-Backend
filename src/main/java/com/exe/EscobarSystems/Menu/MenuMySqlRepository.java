package com.exe.EscobarSystems.Menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository("menu_mysql")
public interface MenuMySqlRepository extends MenuDao, JpaRepository<Menu, Long> {

    @Query(value = "SELECT * FROM #{#entityName} menu " +
            "INNER JOIN menu_category AS menu_category ON menu.menu_category_id = menu_category.menu_category_id " +
            "WHERE menu.is_active=true AND menu_category.menu_category_name = :menuCategoryName",
            nativeQuery = true)
    List<Menu> getMenuBasedOnCategory(@Param("menuCategoryName") String menuCategoryName);

    @Query(value = "SELECT * FROM #{#entityName} menu" +
            " INNER JOIN menu_category AS menu_category ON menu.menu_category_id = menu_category.menu_category_id",
            countQuery = "SELECT COUNT(*) FROM #{#entityName}",
            nativeQuery = true)
    Page<Menu> getAllMenu(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} menu" +
            " INNER JOIN menu_category AS menu_category ON menu.menu_category_id = menu_category.menu_category_id WHERE menu.is_active=true",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    Page<Menu> getAllActiveMenu(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<Menu> getAllActiveMenu();

    @Query(value = "SELECT * FROM #{#entityName} menu" +
            " INNER JOIN menu_category AS menu_category ON menu.menu_category_id = menu_category.menu_category_id WHERE menu.is_active=false",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    Page<Menu> getAllInactiveMenu(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE menu_name IN :menuNames",
            nativeQuery = true)
    void inactivateMenu(@Param("menuNames") List<String> menuNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE menu_name IN :menuNames",
            nativeQuery = true)
    void activateMenu(@Param("menuNames") List<String> menuNames);

    @Modifying
    @Query(value ="INSERT INTO #{#entityName} " +
            "(menu_name, menu_price, menu_category_id, is_active) " +
            "VALUES (:menuName, :menuPrice, :menuCategoryId, :isActive)",
            nativeQuery = true)
    void insertMenu(@Param("menuName")String menuName,
                    @Param("menuPrice") BigDecimal menuPrice,
                    @Param("menuCategoryId")Long menuCategoryId,
                    @Param("isActive")Boolean isActive);

    @Modifying
    @Query(value ="INSERT INTO menu_ingredients " +
            "(menu_id, supply_id, quantity) " +
            "VALUES (:menuId, :supplyId, :quantity)",
            nativeQuery = true)
    void insertIngredient(@Param("menuId")Long menuId,
                          @Param("supplyId")Long supplyId,
                          @Param("quantity")Double quantity);


    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_id = :menuId",
            nativeQuery = true)
    Optional<Menu> getMenuById(@Param("menuId") Long menuId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_name = :menuName",
            nativeQuery = true)
    Optional<Menu> getMenuByName(@Param("menuName") String menuName);
}
