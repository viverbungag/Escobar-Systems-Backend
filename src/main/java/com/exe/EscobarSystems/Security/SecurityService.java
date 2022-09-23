package com.exe.EscobarSystems.Security;


import com.exe.EscobarSystems.Security.Exceptions.UserDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SecurityService {

    @Autowired
    @Qualifier("security_mysql")
    SecurityDao securityRepository;

    private AccountLoginDto convertEntityToDto(Account account){
        return new AccountLoginDto(
                account.getAccountUsername(),
                account.getAccountPassword(),
                String.format("%s, %s",account.getEmployee().getLastName(), account.getEmployee().getFirstName())
        );
    }

    public AccountLoginDto login (AccountLoginDto accountLoginDto){

        Account account = securityRepository
                .findUserByNameAndPassword(accountLoginDto.getAccountUsername(), accountLoginDto.getAccountPassword())
                .orElseThrow(() -> new UserDoesNotExistException());

        return convertEntityToDto(account);

    }
}
