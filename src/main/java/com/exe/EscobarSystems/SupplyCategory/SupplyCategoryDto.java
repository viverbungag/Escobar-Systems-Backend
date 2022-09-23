package com.exe.EscobarSystems.SupplyCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SupplyCategoryDto {

    private Long supplyCategoryId;
    private String supplyCategoryName;
    private Boolean isActive;

}
