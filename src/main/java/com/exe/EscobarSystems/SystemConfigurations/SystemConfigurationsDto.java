package com.exe.EscobarSystems.SystemConfigurations;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SystemConfigurationsDto {

    private String voidPassword;
    private Integer numberOfTables;
}
