package com.exe.EscobarSystems.Supply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("supply_mysql")
public interface SupplyMySqlRepository extends SupplyDao, JpaRepository<Supply, Long> {

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id",
            countQuery = "SELECT COUNT(*) FROM #{#entityName}",
            nativeQuery = true)
    Page<Supply> getAllPagedSupplies(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id",
            countQuery = "SELECT COUNT(*) FROM #{#entityName}",
            nativeQuery = true)
    List<Supply> getAllPagedSupplies();

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id WHERE supply.is_active=true",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE supply.is_active=true",
            nativeQuery = true)
    List<Supply> getAllActiveSuppliesList();

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id WHERE supply.is_active=true",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE supply.is_active=true",
            nativeQuery = true)
    Page<Supply> getAllActivePagedSupplies(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id WHERE supply.is_active=false",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE supply.is_active=false",
            nativeQuery = true)
    Page<Supply> getAllInactivePagedSupplies(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} AS supply" +
            " INNER JOIN supplier AS supplier ON supply.supplier_id = supplier.supplier_id" +
            " INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id" +
            " INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id" +
            " WHERE supply.is_active = true AND supply.supply_quantity <= supply.minimum_quantity",
            countQuery = "SELECT COUNT(*) FROM #{#entityName} WHERE supply.is_active=true AND supply.supply_quantity <= supply.minimum_quantity",
            nativeQuery = true)
    Page<Supply> getAllActiveInMinimumPagedSupplies(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE supply_name IN :supplyNames",
            nativeQuery = true)
    void inactivateSupply(@Param("supplyNames") List<String> supplyNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE supply_name IN :supplyNames",
            nativeQuery = true)
    void activateSupply(@Param("supplyNames") List<String> supplyNames);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supply_name, supply_quantity, minimum_quantity, supplier_id, unit_of_measurement_id, supply_category_id, is_active) " +
            "VALUES (:supplyName, :supplyQuantity, :minimumQuantity, :supplierId, :unitOfMeasurementId, :supplyCategoryId, :isActive)",
            nativeQuery = true)
    void insertSupply(@Param("supplyName")String supplyName,
                      @Param("supplyQuantity")Double supplyQuantity,
                      @Param("minimumQuantity")Double minimumQuantity,
                      @Param("supplierId")Long supplierId,
                      @Param("unitOfMeasurementId")Long unitOfMeasurementId,
                      @Param("supplyCategoryId")Long supplyCategoryId,
                      @Param("isActive")Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supply_id = :supplyId",
            nativeQuery = true)
    Optional<Supply> getSupplyById(@Param("supplyId") Long supplyId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supply_name = :supplyName",
            nativeQuery = true)
    Optional<Supply> getSupplyByName(@Param("supplyName") String supplyName);

}
