package com.exe.EscobarSystems.Supply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplyDto {

    private Long supplyId;
    private String supplyName;
    private Double supplyQuantity;
    private Double minimumQuantity;
    private Boolean inMinimumQuantity;
    private String supplierName;
    private String unitOfMeasurementName;
    private String supplyCategoryName;
    private Boolean isActive;

    public Boolean getInMinimumQuantity() {
        return supplyQuantity <= minimumQuantity;
    }

}
