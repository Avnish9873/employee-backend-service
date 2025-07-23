package com.employee.service.serviceImpl;

import com.employee.service.model.Employee;
import com.employee.service.repository.EmployeeRepository;
import com.employee.service.requestdto.EmployeeRequestDTO;
import com.employee.service.service.EmployeeService;
import com.employee.service.utils.EmployeeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private  EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeValidator = new EmployeeValidator();
    }
  

    @Override
    public Employee createEmployee(EmployeeRequestDTO dto) {
        logger.info("Validating employee data...");

        Map<String, String> errors = employeeValidator.validate(dto);
        if (!errors.isEmpty()) {
            logger.warn("Validation failed with errors: {}", errors);
            throw new IllegalArgumentException(errors.toString());
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        employee.setJoiningDate(dto.getJoiningDate());

        employee.setStatus("Active");
        employee.setCreatedBy(LocalDate.now());
        
        return employeeRepository.save(employee);
    }


    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees.....");
        return employeeRepository.fetchAllEmployees();
    }


    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.fetchEmployeeById(id);
                
    }


    @Override
    public Employee updateEmployee(Long id, EmployeeRequestDTO dto) {
        logger.info("Validating employee update data...");

        Map<String, String> errors = employeeValidator.validate(dto);
        if (!errors.isEmpty()) {
            logger.warn("Validation failed: {}", errors);
            throw new IllegalArgumentException(errors.toString());
        }
        Optional<Employee> existing = employeeRepository.fetchEmployeeById(id);
        if(existing.isEmpty())
        {
        	 throw new RuntimeException("No Data Found with this id :: "+id);
        }
        Employee employee = existing.get();
        
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        employee.setJoiningDate(dto.getJoiningDate());

        logger.info("Saving updated employee: {}", existing);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with ID: {}", id);

        Employee existing = employeeRepository.fetchEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));

        existing.setStatus("InActive");
        employeeRepository.save(existing);
        logger.info("Employee deleted successfully: {}", id);
    }

}
