package com.exe.EscobarSystems.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("employee_mysql")
public interface EmployeeMySqlRepository extends EmployeeDao, JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE employee_first_name = :firstName AND employee_last_name = :lastName",
            nativeQuery = true)
    Optional<Employee> getEmployeeByFirstAndLastName(@Param("firstName") String firstName,
                                                     @Param("lastName") String lastName);
}
