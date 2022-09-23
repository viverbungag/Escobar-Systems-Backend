package com.exe.EscobarSystems.EmployeeType;


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
@Entity(name = "employee_type")
public class EmployeeType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employee_type_id")
    private Long employeeTypeId;

    @NonNull
    @Column(name = "employee_type_name")
    private String employeeTypeName;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

}
