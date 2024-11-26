package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeNotFoundException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.IEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final IEmployeeRepository employeeRepository;
    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(int employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public Employee creat(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    public Employee update(Integer employeeId, Employee employee){
        Employee employeeExisted = employeeRepository.getEmployeeById(employeeId);
        if (employeeExisted == null) {
            throw new EmployeeNotFoundException();
        }

        var nameToUpdate = employee.getName() == null ? employeeExisted.getName() : employee.getName();
        var ageToUpdate = employee.getAge() == null ? employeeExisted.getAge() : employee.getAge();
        var genderToUpdate = employee.getGender() == null ? employeeExisted.getGender() : employee.getGender();
        var salaryToUpdate = employee.getSalary() == null ? employeeExisted.getSalary() : employee.getSalary();

        final var employeeToUpdate = new Employee(employeeId, nameToUpdate, ageToUpdate, genderToUpdate, salaryToUpdate);

        return employeeRepository.updateEmployee(employeeId, employeeToUpdate);
    }
}
