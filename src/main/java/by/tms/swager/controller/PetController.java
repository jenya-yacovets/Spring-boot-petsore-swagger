package by.tms.swager.controller;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import by.tms.swager.exception.PetNotFoundException;
import by.tms.swager.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    public ResponseEntity<?> createPet(@Valid @RequestBody Pet pet) {
        petService.createPet(pet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updatePetInStore() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pet>> getPetByStatus(@RequestParam StatusPet status) {
        List<Pet> byStatus = petService.findByStatus(status);
        return new ResponseEntity<>(byStatus, HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable @Min(1) long petId) {
        if(petId == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            Pet pet = petService.getPet(petId);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{petId}")
    public ResponseEntity<?> updatePet(@PathVariable @Min(1) long petId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable @Min(1) long petId) {
        try {
            petService.deletePet(petId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
