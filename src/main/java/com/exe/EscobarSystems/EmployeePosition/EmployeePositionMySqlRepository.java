package com.exe.EscobarSystems.EmployeePosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("employeePosition_mysql")
public interface EmployeePositionMySqlRepository extends EmployeePositionDao, JpaRepository<EmployeePosition, Long> {

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(employee_position_name, is_active) " +
            "VALUES (:employeePositionName, :isActive)",
            nativeQuery = true)
    void insertEmployeePosition(@Param("employeePositionName")String employeePositionName,
                                @Param("isActive")Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE employee_position_id = :employeePositionId",
            nativeQuery = true)
    Optional<EmployeePosition> getEmployeePositionById(@Param("employeePositionId")Long employeePositionId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE employee_position_name = :employeePositionName",
            nativeQuery = true)
    Optional<EmployeePosition> getEmployeePositionByName(@Param("employeePositionName")String employeePositionName);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE employee_position_name IN :employeePositionNames",
            nativeQuery = true)
    void inactivateEmployeePosition(@Param("employeePositionNames")List<String> employeePositionNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE employee_position_name IN :employeePositionNames",
            nativeQuery = true)
    void activateEmployeePosition(@Param("employeePositionNames")List<String> employeePositionNames);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<EmployeePosition> getAllActiveEmployeePositionsList();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<EmployeePosition> getAllActiveEmployeePositions();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    List<EmployeePosition> getAllInactiveEmployeePositions();
}
