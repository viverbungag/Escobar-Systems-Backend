package com.exe.EscobarSystems.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/active")
    public List<AccountDto> getAllActiveAccounts(){
        return accountService.getAllActiveAccounts();
    }

    @GetMapping("/inactive")
    public List<AccountDto> getAllInactiveAccounts(){
        return accountService.getAllInactiveAccounts();
    }

    @PostMapping("/activate")
    public void activateAccounts(@RequestBody AccountUsernameListDto accountUsernameListDto){
        accountService.activateAccounts(accountUsernameListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateAccounts(@RequestBody AccountUsernameListDto accountUsernameListDto){
        accountService.inactivateAccounts(accountUsernameListDto);
    }

    @PostMapping("/add")
    public void addAccount(@RequestBody AccountDto accountDto){
        accountService.addAccount(accountDto);
    }

    @PutMapping("/update/{id}")
    public void updateAccount(@RequestBody AccountDto accountDto,
                              @PathVariable Long id){
        accountService.updateAccount(accountDto, id);
    }


}
