package com.simonrisberg.critterchronologer.repository;

import com.simonrisberg.critterchronologer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Set<Employee> findEmployeeByDaysAvailable(DayOfWeek dayOfWeek);
}
