package com.simonrisberg.critterchronologer.service;

import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.repository.CustomerRepository;
import com.simonrisberg.critterchronologer.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }

    public Pet findPetById(Long id) throws Exception {
        return petRepository.findById(id).orElseThrow(() -> new Exception("Pet was not found"));
    }

    public List<Pet> listPetsByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);

        Customer owner = savedPet.getOwner();
        owner.addPet(savedPet);
        customerRepository.save(owner);
        return savedPet;

    }
}
