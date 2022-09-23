package com.exe.EscobarSystems.Supply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplyDtoV3 {

    private String supplyName;
    private String unitOfMeasurementAbbreviation;
    private String supplierName;
}
