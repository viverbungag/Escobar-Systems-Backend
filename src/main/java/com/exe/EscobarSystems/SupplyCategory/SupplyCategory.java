package com.exe.EscobarSystems.SupplyCategory;

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
@Entity(name = "supply_category")
public class SupplyCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "supply_category_id")
    private Long supplyCategoryId;

    @NonNull
    @Column(name = "supply_category_name")
    private String supplyCategoryName;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

}
