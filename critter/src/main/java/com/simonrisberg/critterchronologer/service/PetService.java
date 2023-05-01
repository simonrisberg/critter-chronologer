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

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }

    public Pet findPetById(Long id) throws Exception {
        return petRepository.findById(id).orElseThrow(() -> new Exception("Pet was not found"));
    }
}
