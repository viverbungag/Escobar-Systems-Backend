package com.exe.EscobarSystems.Supply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyDao {

    Page<Supply> getAllPagedSupplies(Pageable pageable);

    List<Supply> getAllPagedSupplies();

    Optional<Supply> getSupplyById(Long supplyId);

    Optional<Supply> getSupplyByName(String supplyName);

    void insertSupply(String supplyName,
                      Double supplyQuantity,
                      Double minimumQuantity,
                      Long supplierId,
                      Long unitOfMeasurementId,
                      Long supplyCategoryId,
                      Boolean isActive);

    Page<Supply> getAllActivePagedSupplies(Pageable pageable);

    Page<Supply> getAllInactivePagedSupplies(Pageable pageable);

    void inactivateSupply(List<String> supplyNames);

    void activateSupply(List<String> supplyNames);

    List<Supply> getAllActiveSuppliesList();

    Page<Supply> getAllActiveInMinimumPagedSupplies(Pageable pageable);
}
