package com.exe.EscobarSystems.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SupplierDto {

    private Long supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContactNumber;
    private String supplierContactPerson;
    private Boolean isActive;
}
