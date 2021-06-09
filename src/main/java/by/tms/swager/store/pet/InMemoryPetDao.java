package by.tms.swager.store.pet;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryPetDao implements PetDao{
    private final List<Pet> list = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void save(Pet pet) {
        pet.setId(nextId++);
        list.add(pet);
    }

    @Override
    public void update(Pet pet) {

    }

    @Override
    public List<Pet> findByStatus(StatusPet statusPet) {
        return list.stream().filter(pet -> pet.getStatus().equals(statusPet)).collect(Collectors.toList());
    }

    @Override
    public Pet getById(long id) throws DataNotFoundDaoException {
        Pet findPet = list.stream().filter(pet -> pet.getId() == id).findAny().orElse(null);
        if(findPet == null) throw new DataNotFoundDaoException("Pet not found");
        return findPet;
    }

    @Override
    public void delete(long id) throws DataNotFoundDaoException {
        Pet findPet = getById(id);
        list.remove(findPet);
    }
}
