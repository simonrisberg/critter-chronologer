package com.simonrisberg.critterchronologer.repository;

import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    List<Schedule> findScheduleByPets(Pet pet);
    List<Schedule> findScheduleByEmployees(Employee employee);
}
