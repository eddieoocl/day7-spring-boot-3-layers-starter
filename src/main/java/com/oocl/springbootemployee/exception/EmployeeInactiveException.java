package com.oocl.springbootemployee.exception;

public class EmployeeInactiveException extends Exception {
    public static final String EMPLOYEE_IS_INACTIVE = "Employee is inactive";

    public EmployeeInactiveException() {
        super(EMPLOYEE_IS_INACTIVE);
    }
}
