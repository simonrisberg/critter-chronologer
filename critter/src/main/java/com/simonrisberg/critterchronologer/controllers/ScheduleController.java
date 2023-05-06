package com.simonrisberg.critterchronologer.controllers;

import com.simonrisberg.critterchronologer.dto.ScheduleDTO;
import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.entity.Schedule;
import com.simonrisberg.critterchronologer.service.CustomerService;
import com.simonrisberg.critterchronologer.service.EmployeeService;
import com.simonrisberg.critterchronologer.service.PetService;
import com.simonrisberg.critterchronologer.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final PetService petService;
    private final EmployeeService employeeService;

    private final ScheduleService scheduleService;

    private final CustomerService customerService;

    public ScheduleController(PetService petService, EmployeeService employeeService, ScheduleService scheduleService, CustomerService customerService) {
        this.petService = petService;
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
        this.customerService = customerService;

    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = convertToSchedule(scheduleDTO);

        return convertToScheduleDTO(scheduleService.create(schedule));

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.listSchedules().stream().map(schedule -> convertToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) throws Exception {
        Pet pet = petService.findPetById(petId);

        return scheduleService.findScheduleForPet(pet).stream().map(schedule -> convertToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) throws Exception {
        Employee employee = employeeService.findEmployeeById(employeeId);

        return scheduleService.findScheduleForEmployee(employee).stream().map(schedule -> convertToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);

        return scheduleService.findScheduleForCustomer(customer).stream().map(schedule -> convertToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setSkills(scheduleDTO.getActivities());

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();

        if (petIds != null) {
            petIds.forEach(petId -> {
                try {
                    pets.add(petService.findPetById(petId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        schedule.setPets(pets);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Employee> employees = new ArrayList<>();

        if (employeeIds != null) {
            employeeIds.forEach(employeeId -> {
                try {
                    employees.add(employeeService.findEmployeeById(employeeId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        schedule.setEmployees(employees);

        return schedule;

    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getSkills());

        List<Long> petIds = new ArrayList<>();

        if (schedule.getPets() != null) {
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new ArrayList<>();

        if (schedule.getEmployees() != null) {
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        }

        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }
}
