package com.exe.EscobarSystems.SystemConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("systemConfigurations_mysql")
public interface SystemConfigurationsMySqlRepository extends SystemConfigurationsDao, JpaRepository<SystemConfigurations, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE system_configurations_id = 1",
            nativeQuery = true)
    SystemConfigurations getSystemConfigurations();

}
