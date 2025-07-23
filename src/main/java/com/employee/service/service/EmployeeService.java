package com.employee.service.service;

import java.util.List;
import java.util.Optional;

import com.employee.service.model.Employee;
import com.employee.service.requestdto.EmployeeRequestDTO;

public interface EmployeeService {

	Employee createEmployee(EmployeeRequestDTO employee);

	List<Employee> getAllEmployees();

	Optional<Employee> getEmployeeById(Long id);

	Employee updateEmployee(Long id, EmployeeRequestDTO dto);

	void deleteEmployee(Long id);

	
}
