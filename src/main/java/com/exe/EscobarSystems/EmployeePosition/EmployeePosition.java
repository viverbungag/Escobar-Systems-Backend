package com.exe.EscobarSystems.EmployeePosition;


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
@Entity(name = "employee_position")
public class EmployeePosition {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employee_position_id")
    private Long employeePositionId;

    @NonNull
    @Column(name = "employee_position_name")
    private String employeePositionName;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

}
