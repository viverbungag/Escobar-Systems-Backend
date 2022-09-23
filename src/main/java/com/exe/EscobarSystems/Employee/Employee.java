package com.exe.EscobarSystems.Employee;


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
@Entity(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @NonNull
    @Column(name = "employee_first_name")
    private String firstName;

    @NonNull
    @Column(name = "employee_last_name")
    private String lastName;
}
