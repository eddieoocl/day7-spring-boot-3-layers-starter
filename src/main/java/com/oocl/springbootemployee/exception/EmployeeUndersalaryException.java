package com.oocl.springbootemployee.exception;

public class EmployeeUndersalaryException extends Exception {

    public static final String EMPLOYEE_IS_UNDERSALARY = "Employee is undersalary";

    public EmployeeUndersalaryException() {
        super(EMPLOYEE_IS_UNDERSALARY);
    }
}
