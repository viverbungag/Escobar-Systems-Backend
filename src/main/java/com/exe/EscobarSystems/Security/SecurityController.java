package com.exe.EscobarSystems.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/login")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping
    public AccountLoginDto login(@RequestBody AccountLoginDto accountLoginDto){
        return securityService.login(accountLoginDto);
    }
}
