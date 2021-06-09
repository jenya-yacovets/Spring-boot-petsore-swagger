package by.tms.swager.service;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import by.tms.swager.exception.PetNotFoundException;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import by.tms.swager.store.pet.PetDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetService {
    private final PetDao petDao;

    public PetService(PetDao petDao) {
        this.petDao = petDao;
    }

    public void createPet(Pet pet) {
        petDao.save(pet);
    }

    public Pet getPet(long id) throws PetNotFoundException {
        try {
            return petDao.getById(id);
        } catch (DataNotFoundDaoException e) {
            throw new PetNotFoundException();
        }
    }

    public void deletePet(long id) throws PetNotFoundException {
        try {
            petDao.delete(id);
        } catch (DataNotFoundDaoException e) {
            throw new PetNotFoundException();
        }
    }

    public List<Pet> findByStatus(StatusPet status) {
        return petDao.findByStatus(status);
    }
}
