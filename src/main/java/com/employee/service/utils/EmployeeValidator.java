package com.employee.service.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import com.employee.service.requestdto.EmployeeRequestDTO;


public class EmployeeValidator {

	public Map<String, String> validate(EmployeeRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.put("name", "Name is required");
        }

        if (dto.getEmail() == null || !dto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.put("email", "Valid email is required");
        }

        if (dto.getDepartment() == null || dto.getDepartment().trim().isEmpty()) {
            errors.put("department", "Department is required");
        }

        if (dto.getSalary() == null || dto.getSalary() <= 0) {
            errors.put("salary", "Salary must be greater than 0");
        }

        if (dto.getJoiningDate() == null) {
            errors.put("joiningDate", "Joining date is required");
        } else if (dto.getJoiningDate().isAfter(LocalDate.now())) {
            errors.put("joiningDate", "Joining date cannot be in the future");
        }

        return errors;
    }
}
