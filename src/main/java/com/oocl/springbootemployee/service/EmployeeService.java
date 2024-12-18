package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeAgeNotValidException;
import com.oocl.springbootemployee.exception.EmployeeInactiveException;
import com.oocl.springbootemployee.exception.EmployeeUndersalaryException;
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

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public Employee create(Employee employee) throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeAgeNotValidException();
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000.0) {
            throw new EmployeeUndersalaryException();
        }
        return employeeRepository.addEmployee(employee);
    }

    public Employee update(Integer employeeId, Employee employee) throws EmployeeInactiveException {
        Employee employeeExisted = employeeRepository.getEmployeeById(employeeId);

        if (employeeExisted == null) {
            return null;
        }

        if (!employeeExisted.isActive()) {
            throw new EmployeeInactiveException();
        }

        var nameToUpdate = employee.getName() == null ? employeeExisted.getName() : employee.getName();
        var ageToUpdate = employee.getAge() == null ? employeeExisted.getAge() : employee.getAge();
        var genderToUpdate = employee.getGender() == null ? employeeExisted.getGender() : employee.getGender();
        var salaryToUpdate = employee.getSalary() == null ? employeeExisted.getSalary() : employee.getSalary();

        final var employeeToUpdate = new Employee(employeeId, nameToUpdate, ageToUpdate, genderToUpdate, salaryToUpdate);

        return employeeRepository.updateEmployee(employeeId, employeeToUpdate);
    }
}
