package com.exe.EscobarSystems.SystemConfigurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/system-configurations")
public class SystemConfigurationsController {

    @Autowired
    SystemConfigurationsService systemConfigurationsService;

    @PostMapping("/update")
    public void updateSystemConfigurations(@RequestBody SystemConfigurationsDto systemConfigurationsDto){
        systemConfigurationsService.updateSystemConfigurations(systemConfigurationsDto);
    }

    @GetMapping
    public SystemConfigurationsDto getSystemConfigurations(){
        return systemConfigurationsService.getSystemConfigurations();
    }
}
