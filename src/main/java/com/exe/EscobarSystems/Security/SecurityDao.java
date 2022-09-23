package com.exe.EscobarSystems.Security;

import com.exe.EscobarSystems.Account.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityDao {

    Optional<Account> findUserByNameAndPassword(String accountUsername, String accountPassword);
    Optional<Account> findEmployeeUserByNameAndPassword(String accountUsername, String accountPassword);
    Optional<Account> findAdminUserByNameAndPassword(String accountUsername, String accountPassword);
}
