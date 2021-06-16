package by.tms.swager.repository;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPetsByStatus(StatusPet statusPet);
}
