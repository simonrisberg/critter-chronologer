package com.simonrisberg.critterchronologer.repository;

import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleByPets(Pet pet);
    List<Schedule> findScheduleByEmployees(Employee employee);
}
