package com.exe.EscobarSystems.EmployeeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("employeeType_mysql")
public interface EmployeeTypeMySqlRepository extends EmployeeTypeDao, JpaRepository<EmployeeType, Long> {

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(employee_type_name, is_active) " +
            "VALUES (:employeeTypeName, :isActive)",
            nativeQuery = true)
    void insertEmployeeType(@Param("employeeTypeName")String employeeTypeName,
                                @Param("isActive")Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE employee_type_id = :employeeTypeId",
            nativeQuery = true)
    Optional<EmployeeType> getEmployeeTypeById(@Param("employeeTypeId")Long employeeTypeId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE employee_type_name = :employeeTypeName",
            nativeQuery = true)
    Optional<EmployeeType> getEmployeeTypeByName(@Param("employeeTypeName")String employeeTypeName);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE employee_type_name IN :employeeTypeNames",
            nativeQuery = true)
    void inactivateEmployeeType(@Param("employeeTypeNames") List<String> employeeTypeNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE employee_type_name IN :employeeTypeNames",
            nativeQuery = true)
    void activateEmployeeType(@Param("employeeTypeNames")List<String> employeeTypeNames);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<EmployeeType> getAllActiveEmployeeTypesList();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<EmployeeType> getAllActiveEmployeeTypes();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    List<EmployeeType> getAllInactiveEmployeeTypes();
}
