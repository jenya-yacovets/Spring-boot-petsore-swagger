package by.tms.swager.store.pet;

import by.tms.swager.entity.Pet;
import by.tms.swager.entity.StatusPet;
import by.tms.swager.exception.dao.DataNotFoundDaoException;

import java.util.List;

public interface PetDao {
    void save(Pet pet);
    void update(Pet pet);
    List<Pet> findByStatus(StatusPet statusPet);
    Pet getById(long id) throws DataNotFoundDaoException;
    void delete(long id) throws DataNotFoundDaoException;
}
