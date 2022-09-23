package com.exe.EscobarSystems.EmployeeAttendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository("employeeAttendance_mysql")
public interface EmployeeAttendanceMySqlRepository extends EmployeeAttendanceDao, JpaRepository<EmployeeAttendance, Long> {

    @Modifying
    @Query(value="INSERT INTO #{#entityName}(attendance_time, attendance_type) VALUES (:attendanceTime, :attendanceType)",
            nativeQuery = true)
    void insertAttendance(@Param("attendanceTime") LocalDateTime attendanceTime,
                          @Param("attendanceType") String attendanceType);

    @Query(value="SELECT * FROM #{#entityName} WHERE attendance_time = :attendanceTime",
            nativeQuery = true)
    Optional<EmployeeAttendance> getEmployeeAttendanceByAttendanceTime(LocalDateTime attendanceTime);
}
