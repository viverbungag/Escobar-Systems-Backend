package com.exe.EscobarSystems.EmployeeAttendance;

public enum AttendanceType {
    CHECK_IN("CHECK_IN"),
    CHECK_OUT("CHECK_OUT");

    private final String string;

    AttendanceType(final String string){
        this.string = string;
    }

    @Override
    public String toString(){
        return string;
    }
}
