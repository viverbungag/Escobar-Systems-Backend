package com.exe.EscobarSystems.Supplier;

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
@Entity(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @NonNull
    @Column(name = "supplier_name")
    private String supplierName;

    @NonNull
    @Column(name = "supplier_address")
    private String supplierAddress;

    @NonNull
    @Column(name = "supplier_contact_number")
    private String supplierContactNumber;

    @NonNull
    @Column(name = "supplier_contact_person")
    private String supplierContactPerson;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
