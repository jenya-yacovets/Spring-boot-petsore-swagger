package by.tms.swager.service;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import by.tms.swager.exception.PetNotFoundException;
import by.tms.swager.repository.CategoryRepository;
import by.tms.swager.repository.PetRepository;
import by.tms.swager.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public PetService(PetRepository petRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public void createPet(Pet pet) {
        if(pet.getCategory() != null && pet.getCategory().getId() == 0) {
            pet.setCategory(categoryRepository.save(pet.getCategory()));
        }

        for(int i = 0; i < pet.getTags().size(); i++) {
            if(pet.getTags().get(i).getId() == 0) {
                pet.getTags().set(i, tagRepository.save(pet.getTags().get(i)));
            }
        }

        petRepository.save(pet);
    }

    public Pet getPet(long id) throws PetNotFoundException {
        return petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public void deletePet(long id) throws PetNotFoundException {
        getPet(id);
        petRepository.deleteById(id);
    }

    public List<Pet> findByStatus(StatusPet status) {
        return petRepository.findPetsByStatus(status);
    }
}
