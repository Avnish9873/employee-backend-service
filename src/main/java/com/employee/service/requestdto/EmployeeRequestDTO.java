package com.employee.service.requestdto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

	 private String name;
	 private String email;
	 private String department;
	 private Double salary;

     @JsonFormat(pattern = "dd-MM-yyyy")
	 private LocalDate joiningDate;
}
