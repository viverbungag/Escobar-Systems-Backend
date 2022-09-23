package com.exe.EscobarSystems.EmployeeAttendance;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "employee_attendance")
public class EmployeeAttendance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employee_attendance_id")
    private Long employeeAttendanceId;

    @NonNull
    @Column(name = "attendance_time")
    private LocalDateTime attendanceTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_type")
    private AttendanceType attendanceType;

}
