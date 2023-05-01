package com.simonrisberg.critterchronologer.repository;

import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByPets(Pet pet);
}
