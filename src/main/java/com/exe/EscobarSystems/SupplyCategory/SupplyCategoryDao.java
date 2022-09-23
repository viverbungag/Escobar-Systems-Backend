package com.exe.EscobarSystems.SupplyCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyCategoryDao {

    Page<SupplyCategory> getAllSupplyCategories(Pageable pageable);

    void insertSupplyCategory(String supplyCategoryName, Boolean isActive);

    Optional<SupplyCategory> getSupplyCategoryById(Long supplyCategoryId);

    Optional<SupplyCategory> getSupplyCategoryByName(String supplyCategoryName);

    Page<SupplyCategory> getAllActiveSupplyCategories(Pageable pageable);

    Page<SupplyCategory> getAllInactiveSupplyCategories(Pageable pageable);

    void inactivateSupplyCategory(List<String> supplyCategoryNames);

    void activateSupplyCategory(List<String> supplyCategoryNames);

    List<SupplyCategory> getAllActiveSupplyCategoriesList();
}
