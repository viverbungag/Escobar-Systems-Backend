package com.exe.EscobarSystems.UnitOfMeasurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("unitOfMeasurement_mysql")
public interface UnitOfMeasurementMySqlRepository extends UnitOfMeasurementDao, JpaRepository<UnitOfMeasurement, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<UnitOfMeasurement> getAllUnitOfMeasurements(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<UnitOfMeasurement> getAllActiveUnitOfMeasurementsList();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    Page<UnitOfMeasurement> getAllActiveUnitOfMeasurements(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    Page<UnitOfMeasurement> getAllInactiveUnitOfMeasurements(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE unit_of_measurement_name IN :unitOfMeasurementNames",
            nativeQuery = true)
    void inactivateUnitOfMeasurement(@Param("unitOfMeasurementNames") List<String> unitOfMeasurementNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE unit_of_measurement_name IN :unitOfMeasurementNames",
            nativeQuery = true)
    void activateUnitOfMeasurement(@Param("unitOfMeasurementNames") List<String> unitOfMeasurementNames);


    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(unit_of_measurement_name, unit_of_measurement_abbreviation, is_active) " +
            "VALUES (:unitOfMeasurementName, :unitOfMeasurementAbbreviation, :isActive)",
            nativeQuery = true)
    void insertUnitOfMeasurement(@Param("unitOfMeasurementName") String unitOfMeasurementName,
                                 @Param("unitOfMeasurementAbbreviation") String unitOfMeasurementAbbreviation,
                                 @Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} WHERE unit_of_measurement_id = :unitOfMeasurementId",
            nativeQuery = true)
    Optional<UnitOfMeasurement> getUnitOfMeasurementById(@Param("unitOfMeasurementId") Long unitOfMeasurementId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE unit_of_measurement_name = :unitOfMeasurementName",
            nativeQuery = true)
    Optional<UnitOfMeasurement> getUnitOfMeasurementByName(@Param("unitOfMeasurementName") String unitOfMeasurementName);
}
