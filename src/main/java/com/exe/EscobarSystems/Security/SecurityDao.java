package com.exe.EscobarSystems.Security;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityDao {

    Optional<Account> findUserByNameAndPassword(String accountUsername, String accountPassword);
}
