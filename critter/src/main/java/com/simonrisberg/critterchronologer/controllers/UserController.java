package com.simonrisberg.critterchronologer.controllers;

import com.simonrisberg.critterchronologer.dto.CustomerDTO;
import com.simonrisberg.critterchronologer.dto.EmployeeDTO;
import com.simonrisberg.critterchronologer.dto.EmployeeRequestDTO;
import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Employee;
import com.simonrisberg.critterchronologer.entity.EmployeeSkill;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.service.CustomerService;
import com.simonrisberg.critterchronologer.service.EmployeeService;
import com.simonrisberg.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final PetService petService;

    private final CustomerService customerService;

    private final EmployeeService employeeService;

    public UserController(PetService petService, CustomerService customerService, EmployeeService employeeService) {
        this.petService = petService;
        this.customerService = customerService;
        this.employeeService = employeeService;

    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertToCustomer(customerDTO);
        return convertToCustomerDTO(customerService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.fetchCustomers().stream().map(customer -> convertToCustomerDTO(customer)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Exception {
        Pet pet = petService.findPetById(petId);

        Customer customer = customerService.findCustomerByPet(pet);

        return convertToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        return convertToEmployeeDTO(employeeService.save(employee));

    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Exception {
        return convertToEmployeeDTO(employeeService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Exception {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        HashSet<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());

        return employeeService.findEmployeesBySkills(employeeDTO.getDate(), skills).stream().map(employee -> convertToEmployeeDTO(employee)).collect(Collectors.toList());
    }

    public Customer convertToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setName(customerDTO.getName());

        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null) {
            List<Pet> pets = petService.listPets();
            customer.setPets(pets);
        }

        return customer;
    }

    public CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = new ArrayList<>();

        if (customer.getPets() != null){
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        return employee;
    }

    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());

        return employeeDTO;
    }

}
