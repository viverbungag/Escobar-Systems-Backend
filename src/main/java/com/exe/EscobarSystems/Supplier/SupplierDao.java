package com.exe.EscobarSystems.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierDao {

    Page<Supplier> getAllSuppliers(Pageable pageable);

    void insertSupplier(String supplierName,
                        String supplierAddress,
                        String supplierContactNumber,
                        String supplierContactPerson,
                        Boolean isActive);

    Optional<Supplier> getSupplierById(Long supplierId);

    Optional<Supplier> getSupplierByName(String supplierName);

    Page<Supplier> getAllActiveSuppliers(Pageable pageable);

    Page<Supplier> getAllInactiveSuppliers(Pageable pageable);

    void inactivateSupplier(List<String> supplierNames);

    void activateSupplier(List<String> supplierNames);

    List<Supplier> getAllActiveSupplierList();

}
