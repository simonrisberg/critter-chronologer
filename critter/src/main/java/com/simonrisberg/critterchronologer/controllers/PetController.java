package com.simonrisberg.critterchronologer.controllers;

import com.simonrisberg.critterchronologer.dto.PetDTO;
import com.simonrisberg.critterchronologer.entity.Customer;
import com.simonrisberg.critterchronologer.entity.Pet;
import com.simonrisberg.critterchronologer.service.CustomerService;
import com.simonrisberg.critterchronologer.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final CustomerService customerService;

    private final PetService petService;

    public PetController(CustomerService customerService, PetService petService) {
        this.customerService = customerService;
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer owner = customerService.findById(petDTO.getOwnerId());

        Pet pet = convertToPet(petDTO);
        pet.setOwner(owner);

        return convertToPetDTO(petService.savePet(pet));

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) throws Exception {
        Pet pet = petService.findPetById(petId);
        return convertToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.listPets().stream().map(pet -> convertToPetDTO(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.listPetsByOwnerId(ownerId).stream().map(pet -> convertToPetDTO(pet)).collect(Collectors.toList());
    }

    private Pet convertToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setId(petDTO.getId());
        pet.setType(petDTO.getType());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());

        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        petDTO.setName(pet.getName());
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setOwnerId(pet.getOwner().getId());

        return petDTO;
    }
}
