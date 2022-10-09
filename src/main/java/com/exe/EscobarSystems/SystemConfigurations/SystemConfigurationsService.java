package com.exe.EscobarSystems.SystemConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SystemConfigurationsService {

    @Autowired
    @Qualifier("systemConfigurations_mysql")
    SystemConfigurationsDao systemConfigurationsRepository;

    private SystemConfigurationsDto convertEntityToDto(SystemConfigurations systemConfigurations){
        return new SystemConfigurationsDto(
           systemConfigurations.getVoidPassword(),
           systemConfigurations.getNumberOfTables()
        );
    }

    public void updateSystemConfigurations(SystemConfigurationsDto systemConfigurationsDto){
        SystemConfigurations systemConfigurations = systemConfigurationsRepository.getSystemConfigurations();
        String voidPassword = systemConfigurationsDto.getVoidPassword();
        Integer numberOfTables = systemConfigurationsDto.getNumberOfTables();

        systemConfigurations.setVoidPassword(voidPassword);
        systemConfigurations.setNumberOfTables(numberOfTables);
    }

    public SystemConfigurationsDto getSystemConfigurations(){
        return convertEntityToDto(systemConfigurationsRepository.getSystemConfigurations());
    }
}
