package com.exe.EscobarSystems.UnitOfMeasurement;


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
@Entity(name = "unit_of_measurement")
public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "unit_of_measurement_id")
    private Long unitOfMeasurementId;

    @NonNull
    @Column(name = "unit_of_measurement_name")
    private String unitOfMeasurementName;

    @NonNull
    @Column(name = "unit_of_measurement_abbreviation")
    private String unitOfMeasurementAbbreviation;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

}
