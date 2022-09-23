package com.exe.EscobarSystems.UnitOfMeasurement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UnitOfMeasurementDto {

    private Long unitOfMeasurementId;
    private String unitOfMeasurementName;
    private String unitOfMeasurementAbbreviation;
    private Boolean isActive;
}
