package com.exe.EscobarSystems.EmployeeAttendanceJoin;

import com.exe.EscobarSystems.Employee.Employee;
import com.exe.EscobarSystems.EmployeeAttendance.EmployeeAttendance;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity(name = "employee_attendance_join")
public class EmployeeAttendanceJoin {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employee_attendance_join_id")
    private Long employeeAttendanceJoinId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "employee_attendance_id")
    private EmployeeAttendance employeeAttendance;

}
