package com.exe.EscobarSystems.Security;


import com.exe.EscobarSystems.Employee.Employee;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @NonNull
    @Column(name = "account_username")
    private String accountUsername;

    @NonNull
    @Column(name = "account_password")
    private String accountPassword;

    @NonNull
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
