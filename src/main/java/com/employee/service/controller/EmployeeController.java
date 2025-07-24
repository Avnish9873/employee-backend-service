package com.employee.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.service.model.Employee;
import com.employee.service.requestdto.EmployeeRequestDTO;
import com.employee.service.responsedto.ApiResponseEntity;
import com.employee.service.service.EmployeeService;


@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private  EmployeeService employeeService;
	
	@PostMapping("/save")
	public ResponseEntity<?> createEmployee(@ModelAttribute EmployeeRequestDTO dto) {
	    try {
	        logger.info("Received request to create employee");

	        Employee saved = employeeService.createEmployee(dto);
	        return ResponseEntity.ok().body(ApiResponseEntity.getStringObject("Employee Added Successfully", saved));

	    } catch (IllegalArgumentException ex) {
	        logger.warn("Validation failed: {}", ex.getMessage());
	        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));

	    } catch (Exception ex) {
	        logger.error("Unexpected error: {}", ex.getMessage(), ex);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
	    }
	}

	
	@GetMapping("get-all-data")
    public ResponseEntity<?> getAllEmployees() {
        try {
            logger.info("Fetching all employees...");
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                logger.warn("No employees found.");
                return ResponseEntity.status(204).body(Map.of("message", "No data available"));
            }
            logger.info("Employees found: {}", employees.size());
            return ResponseEntity.ok().body(ApiResponseEntity.getListStringObject(employees));
        } catch (Exception e) {
            logger.error("Error fetching employees: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Unable to fetch employee data"));
        }
    }
	
	@GetMapping("get-data-by-Id/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
	    try {
	        logger.info("Received request to fetch employee with ID: {}", id);
	        Optional<Employee> employee = employeeService.getEmployeeById(id);
	        return ResponseEntity.ok().body(ApiResponseEntity.getListStringObject(employee));
	        
	    } catch (NoSuchElementException ex) {
	        logger.warn("Employee not found: {}", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));

	    } catch (Exception ex) {
	        logger.error("Error fetching employee: {}", ex.getMessage(), ex);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "Unable to fetch employee"));
	    }
	}
	
	@PutMapping("/update-employee-by-Id/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeRequestDTO dto) {
	    try {
	        logger.info("Received request to update employee with ID: {}", id);
	        Employee updatedEmployee = employeeService.updateEmployee(id, dto);
	        
	        return ResponseEntity.ok().body(ApiResponseEntity.getStringObject("Employee Updated Successfully", updatedEmployee));
	    } catch (NoSuchElementException ex) {
	        logger.warn("Update failed: {}", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));

	    } catch (IllegalArgumentException ex) {
	        logger.warn("Validation failed: {}", ex.getMessage());
	        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));

	    } catch (Exception ex) {
	        logger.error("Unexpected error while updating employee: {}", ex.getMessage(), ex);
	        return ResponseEntity.internalServerError().body(Map.of("error", "Internal Server Error"));
	    }
	}

	@DeleteMapping("delete-employee-by-Id/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
	    try {
	        logger.info("Received request to delete employee with ID: {}", id);
	        employeeService.deleteEmployee(id);
	        return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));

	    } catch (NoSuchElementException ex) {
	        logger.warn("Delete failed: {}", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Map.of("error", ex.getMessage()));

	    } catch (Exception ex) {
	        logger.error("Unexpected error while deleting employee: {}", ex.getMessage(), ex);
	        return ResponseEntity.internalServerError()
	                .body(Map.of("error", "Internal Server Error"));
	    }
	}


}
