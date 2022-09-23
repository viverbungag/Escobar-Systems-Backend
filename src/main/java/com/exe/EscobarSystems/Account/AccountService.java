package com.exe.EscobarSystems.Account;

import com.exe.EscobarSystems.Account.Exceptions.AccountIsExistingException;
import com.exe.EscobarSystems.Account.Exceptions.AccountNotFoundException;
import com.exe.EscobarSystems.Account.Exceptions.PasswordIsNullException;
import com.exe.EscobarSystems.Account.Exceptions.UsernameIsNullException;
import com.exe.EscobarSystems.Employee.Employee;
import com.exe.EscobarSystems.Employee.EmployeeDao;
import com.exe.EscobarSystems.Employee.Exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    @Autowired
    @Qualifier("account_mysql")
    AccountDao accountRepository;

    @Autowired
    @Qualifier("employee_mysql")
    EmployeeDao employeeRepository;

    private AccountDto convertEntityToDto(Account account){
        return new AccountDto(
                account.getAccountId(),
                account.getAccountUsername(),
                account.getAccountPassword(),
                String.format("%s, %s",account.getEmployee().getEmployeeLastName(), account.getEmployee().getEmployeeFirstName()),
                account.getAccessInventoryManagementSystem(),
                account.getAccessEmployeeManagementSystem(),
                account.getAccessIncomeAndExpenseSystem(),
                account.getAccessOrderingSystem(),
                account.getIsActive()
        );
    }

    public List<AccountDto> getAllActiveAccounts(){
        return accountRepository
                .getAllActiveAccounts()
                .stream()
                .map((account) -> convertEntityToDto(account))
                .collect(Collectors.toList());
    }

    public List<AccountDto> getAllInactiveAccounts(){
        return accountRepository
                .getAllInactiveAccounts()
                .stream()
                .map((account) -> convertEntityToDto(account))
                .collect(Collectors.toList());
    }

    public void inactivateAccounts(AccountUsernameListDto accountUsernameListDto){
        accountRepository.inactivateAccount(accountUsernameListDto.getAccountUsernames());
    }

    public void activateAccounts(AccountUsernameListDto accountUsernameListDto){
        accountRepository.activateAccount(accountUsernameListDto.getAccountUsernames());
    }


    public void addAccount(AccountDto accountDto){
        String username = accountDto.getAccountUsername();
        String password = accountDto.getAccountPassword();
        Boolean accessInventoryManagementSystem = accountDto.getAccessInventoryManagementSystem();
        Boolean accessEmployeeManagementSystem = accountDto.getAccessEmployeeManagementSystem();
        Boolean accessIncomeAndExpenseSystem = accountDto.getAccessIncomeAndExpenseSystem();
        Boolean accessOrderingSystem = accountDto.getAccessOrderingSystem();
        Boolean isActive = accountDto.getIsActive();



        String employeeFullName = accountDto.getEmployeeName();
        String[] employeeSplit = employeeFullName.split(", ");
        String employeeLastName = employeeSplit[0];
        String employeeFirstName = employeeSplit[1];

        Employee employee = employeeRepository
                .getEmployeeByFirstAndLastName(employeeFirstName, employeeLastName)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeFirstName, employeeLastName));

        if (username == null || username.length() <= 0){
            throw new UsernameIsNullException();
        }

        if (password == null || password.length() <= 0){
            throw new PasswordIsNullException();
        }

        Optional<Account> accountOptional = accountRepository
                .getAccountByName(username);

        if (accountOptional.isPresent()){
            throw new AccountIsExistingException(username);
        }

        accountRepository.addAccount(username,
                password,
                employee.getEmployeeId(),
                accessInventoryManagementSystem,
                accessEmployeeManagementSystem,
                accessIncomeAndExpenseSystem,
                accessOrderingSystem,
                isActive);
    }

    public void updateAccount(AccountDto accountDto, Long id){
        Account account = accountRepository
                .getAccountById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        String username = accountDto.getAccountUsername();
        String password = accountDto.getAccountPassword();
        Boolean accessInventoryManagementSystem = accountDto.getAccessInventoryManagementSystem();
        Boolean accessEmployeeManagementSystem = accountDto.getAccessEmployeeManagementSystem();
        Boolean accessIncomeAndExpenseSystem = accountDto.getAccessIncomeAndExpenseSystem();
        Boolean accessOrderingSystem = accountDto.getAccessOrderingSystem();
        Boolean isActive = accountDto.getIsActive();

        String employeeFullName = accountDto.getEmployeeName();
        String[] employeeSplit = employeeFullName.split(", ");
        String employeeLastName = employeeSplit[0];
        String employeeFirstName = employeeSplit[1];

        Employee employee = employeeRepository
                .getEmployeeByFirstAndLastName(employeeFirstName, employeeLastName)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeFirstName, employeeLastName));

        if (username == null || username.length() <= 0){
            throw new UsernameIsNullException();
        }

        if (password == null || password.length() <= 0){
            throw new PasswordIsNullException();
        }

        if (!Objects.equals(account.getAccountUsername(), username)){

            Optional<Account> accountOptional = accountRepository
                    .getAccountByName(username);

            if (accountOptional.isPresent()){
                throw new AccountIsExistingException(username);
            }

            account.setAccountUsername(username);
        }

        account.setAccountPassword(password);
        account.setAccessInventoryManagementSystem(accessInventoryManagementSystem);
        account.setAccessEmployeeManagementSystem(accessEmployeeManagementSystem);
        account.setAccessIncomeAndExpenseSystem(accessIncomeAndExpenseSystem);
        account.setAccessOrderingSystem(accessOrderingSystem);
        account.setIsActive(isActive);
        account.setEmployee(employee);
    }
}
