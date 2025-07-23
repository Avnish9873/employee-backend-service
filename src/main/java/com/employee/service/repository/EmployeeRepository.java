package com.employee.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.service.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	
	@Query("SELECT e FROM Employee e ORDER BY e.id DESC")
    List<Employee> fetchAllEmployees();

	@Query("SELECT e FROM Employee e WHERE e.id = :id")
	Optional<Employee> fetchEmployeeById(@Param("id") Long id);

	
}
