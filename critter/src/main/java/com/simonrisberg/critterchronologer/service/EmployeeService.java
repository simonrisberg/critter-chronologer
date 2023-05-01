package com.simonrisberg.critterchronologer.service;

import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.EmployeeSkill;
import com.simonrisberg.critterchronologer.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id) throws Exception {
        return employeeRepository.findById(id).orElseThrow(() -> new Exception("Employee was not found"));
    }

    public void setEmployeeAvailability(Set<DayOfWeek> employeeAvailability, Long employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new Exception("Employee not found"));

        employee.setDaysAvailable(employeeAvailability);
    }

    public Set<Employee> findEmployeesBySkills(LocalDate localDate, HashSet<EmployeeSkill> skills) {
        Set<Employee> employeesWithSkills = new HashSet<>();
        Set<Employee> availableEmployees = employeeRepository.findEmployeeByDaysAvailable(localDate.getDayOfWeek());

        availableEmployees.forEach(employee -> {
            boolean isMatched = employee.getSkills().containsAll(skills);

            if (isMatched) {
                employeesWithSkills.add(employee);
            }
        });

        return employeesWithSkills;
    }
}
