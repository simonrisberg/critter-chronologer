package com.simonrisberg.critterchronologer.service;

import com.simonrisberg.critterchronologer.dto.CustomerDTO;
import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> fetchCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerByPet(Pet pet) {
        return customerRepository.findCustomerByPets(pet);
    }

    public Customer findById(Long id) {
        return customerRepository.getOne(id);
    }



}
