package com.exe.EscobarSystems.SystemConfigurations;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "system_configurations")
public class SystemConfigurations {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "system_configurations_id")
    private Long systemConfigurationsId;

    @NonNull
    @Column(name = "void_password")
    private String voidPassword;

    @NonNull
    @Column(name = "number_of_tables")
    private Integer numberOfTables;
}
