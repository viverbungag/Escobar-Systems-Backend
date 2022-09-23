package com.exe.EscobarSystems.Security;

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
}
