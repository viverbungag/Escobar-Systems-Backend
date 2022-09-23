package com.exe.EscobarSystems.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("employee_mysql")
public interface EmployeeMySqlRepository extends EmployeeDao, JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<Employee> getAllActiveEmployees();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    List<Employee> getAllInactiveEmployees();

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE employee_id IN :employeeIds",
            nativeQuery = true)
    void inactivateEmployees(@Param("employeeIds")List<Long> employeeIds);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE employee_id IN :employeeIds",
            nativeQuery = true)
    void activateEmployees(@Param("employeeIds")List<Long> employeeIds);


    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(employee_first_name, employee_last_name, employee_address, employee_contact_number, date_employed, employee_position_id, employee_type_id, superior_employee_id, is_active) " +
            "VALUES (:employeeFirstName, :employeeLastName, :employeeAddress, :employeeContactNumber, :dateEmployed, :employeePositionId, :employeeTypeId, :superiorEmployee, :isActive)",
            nativeQuery = true)
    void insertEmployees(@Param("employeeFirstName")String employeeFirstName,
                         @Param("employeeLastName")String employeeLastName,
                         @Param("employeeAddress")String employeeAddress,
                         @Param("employeeContactNumber")String employeeContactNumber,
                         @Param("dateEmployed")LocalDateTime dateEmployed,
                         @Param("employeePositionId")Long employeePositionId,
                         @Param("employeeTypeId")Long employeeTypeId,
                         @Param("superiorEmployee")Long superiorEmployee,
                         @Param("isActive")Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} WHERE employee_id = :employeeId",
            nativeQuery = true)
    Optional<Employee> getEmployeeById(@Param("employeeId")Long employeeId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE employee_first_name = :firstName AND employee_last_name = :lastName",
            nativeQuery = true)
    Optional<Employee> getEmployeeByFirstAndLastName(@Param("firstName")String firstName,
                                                     @Param("lastName")String lastName);
}
