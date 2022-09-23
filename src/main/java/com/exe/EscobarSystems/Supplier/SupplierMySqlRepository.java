package com.exe.EscobarSystems.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("supplier_mysql")
public interface SupplierMySqlRepository extends SupplierDao, JpaRepository<Supplier, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<Supplier> getAllSuppliers(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<Supplier> getAllActiveSupplierList();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active = true",
            nativeQuery = true)
    Page<Supplier> getAllActiveSuppliers(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active = false",
            nativeQuery = true)
    Page<Supplier> getAllInactiveSuppliers(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE supplier_name IN :supplierNames",
            nativeQuery = true)
    void inactivateSupplier(@Param("supplierNames") List<String> supplierNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE supplier_name IN :supplierNames",
            nativeQuery = true)
    void activateSupplier(@Param("supplierNames") List<String> supplierNames);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supplier_name, supplier_address, supplier_contact_number, supplier_contact_person, is_active) " +
            "VALUES (:supplierName, :supplierAddress, :supplierContactNumber, :supplierContactPerson, :isActive)",
            nativeQuery = true)
    void insertSupplier(@Param("supplierName") String supplierName,
                        @Param("supplierAddress") String supplierAddress,
                        @Param("supplierContactNumber") String supplierContactNumber,
                        @Param("supplierContactPerson") String supplierContactPerson,
                        @Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supplier_id = :supplierId",
            nativeQuery = true)
    Optional<Supplier> getSupplierById(@Param("supplierId") Long supplierId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supplier_name = :supplierName",
            nativeQuery = true)
    Optional<Supplier> getSupplierByName(@Param("supplierName") String supplierName);




}
