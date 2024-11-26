package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeAgeNotValidException;
import com.oocl.springbootemployee.exception.EmployeeInactiveException;
import com.oocl.springbootemployee.exception.EmployeeUndersalaryException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    @Test
    void should_return_the_given_employees_when_getAllEmployees() {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        when(mockedEmployeeRepository.getAll()).thenReturn(List.of(new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(1, allEmployees.size());
        assertEquals("Lucy", allEmployees.get(0).getName());
    }

    @Test
    void should_return_the_created_employee_when_create_given_a_employee() throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        Employee createdEmployee = employeeService.create(lucy);

        //then
        assertEquals("Lucy", createdEmployee.getName());
    }

    @Test
    void should_throw_EmployeeAgeNotValidException_when_create_given_an_underage_employee() throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 17, Gender.FEMALE, 21000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        //then
        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(lucy));
        verify(mockedEmployeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_throw_EmployeeAgeNotValidException_when_create_given_an_overage_employee() throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 66, Gender.FEMALE, 21000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        //then
        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(lucy));
        verify(mockedEmployeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_throw_EmployeeUndersalaryException_when_create_given_an_undersalary_employee() throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 31, Gender.FEMALE, 8000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        //then
        assertThrows(EmployeeUndersalaryException.class, () -> employeeService.create(lucy));
        verify(mockedEmployeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_create_active_employee_when_create_employee() throws EmployeeAgeNotValidException, EmployeeUndersalaryException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        Employee lucy = new Employee(1, "Lucy", 31, Gender.FEMALE, 8000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        //then
        verify(mockedEmployeeRepository, never()).addEmployee(argThat(Employee::isActive));
    }

    @Test
    void should_throw_EmployeeInactiveException_when_update_inactive_employee() throws EmployeeInactiveException {
        //given
        IEmployeeRepository mockedEmployeeRepository = mock(IEmployeeRepository.class);
        int id = 1;
        Employee lucy = new Employee(id, "Lucy", 31, Gender.FEMALE, 8000.0);
        lucy.setActive(false);
        when(mockedEmployeeRepository.getEmployeeById(id)).thenReturn(lucy);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        //then
        assertThrows(EmployeeInactiveException.class, () -> employeeService.update(lucy.getId(), lucy));
        verify(mockedEmployeeRepository, never()).updateEmployee(any(), any());
    }
}
