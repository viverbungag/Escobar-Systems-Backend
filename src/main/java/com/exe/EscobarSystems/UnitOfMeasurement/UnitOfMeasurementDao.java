package com.exe.EscobarSystems.UnitOfMeasurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitOfMeasurementDao {

    void insertUnitOfMeasurement(String unitOfMeasurementName,
                                 String unitOfMeasurementAbbreviation,
                                 Boolean isActive);

    Optional<UnitOfMeasurement> getUnitOfMeasurementById(Long unitOfMeasurementId);

    Optional<UnitOfMeasurement> getUnitOfMeasurementByName(String unitOfMeasurementName);

    Page<UnitOfMeasurement> getAllUnitOfMeasurements(Pageable pageable);

    Page<UnitOfMeasurement> getAllActiveUnitOfMeasurements(Pageable pageable);

    Page<UnitOfMeasurement> getAllInactiveUnitOfMeasurements(Pageable pageable);

    void inactivateUnitOfMeasurement(List<String> unitOfMeasurementNames);

    void activateUnitOfMeasurement(List<String> unitOfMeasurementNames);

    List<UnitOfMeasurement> getAllActiveUnitOfMeasurementsList();

}
