package com.simonrisberg.critterchronologer.service;

import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.entity.Schedule;
import com.simonrisberg.critterchronologer.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> listSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleForPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    public List<Schedule> findScheduleForEmployee(Employee employee) {
        return scheduleRepository.findScheduleByEmployees(employee);
    }

    public List<Schedule> findScheduleForCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();

        List<Schedule> schedules = new LinkedList<>();

        pets.forEach(pet -> {
            List<Schedule> petsSchedule = scheduleRepository.findScheduleByPets(pet);
            schedules.addAll(petsSchedule);
        });

        return schedules;
    }
}
