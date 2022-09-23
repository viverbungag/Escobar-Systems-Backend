package com.exe.EscobarSystems.Security;

import com.exe.EscobarSystems.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("security_mysql")
public interface SecurityMySqlRepository extends SecurityDao, JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE account_username = :accountUsername AND account_password = :accountPassword",
            nativeQuery = true)
    Optional<Account> findUserByNameAndPassword(@Param("accountUsername") String accountUsername,
                                                @Param("accountPassword") String accountPassword);

    @Query(value = "SELECT * FROM #{#entityName} AS account " +
            "INNER JOIN employee AS employee ON account.employee_id = employee.employee_id " +
            "INNER JOIN employee_type as employee_type ON employee.employee_type_id = employee_type.employee_type_id " +
            "WHERE account_username = :accountUsername AND account_password = :accountPassword " +
            "AND employee_type_name != 'Owner' AND account.is_active = true",
            nativeQuery = true)
    Optional<Account> findEmployeeUserByNameAndPassword(@Param("accountUsername") String accountUsername,
                                                        @Param("accountPassword") String accountPassword);

    @Query(value = "SELECT * FROM #{#entityName} AS account " +
            "INNER JOIN employee AS employee ON account.employee_id = employee.employee_id " +
            "INNER JOIN employee_type AS employee_type ON employee.employee_type_id = employee_type.employee_type_id " +
            "WHERE account_username = :accountUsername AND account_password = :accountPassword " +
            "AND employee_type_name = 'Owner' AND account.is_active = true",
            nativeQuery = true)
    Optional<Account> findAdminUserByNameAndPassword(@Param("accountUsername") String accountUsername,
                                                     @Param("accountPassword") String accountPassword);
}
