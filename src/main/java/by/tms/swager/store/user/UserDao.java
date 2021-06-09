package by.tms.swager.store.user;

import by.tms.swager.entity.User;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import by.tms.swager.exception.dao.DuplicationDataDaoException;

public interface UserDao {
    void save(User user) throws DuplicationDataDaoException;
    void delete(long id) throws DataNotFoundDaoException;
    void delete(String username) throws DataNotFoundDaoException;
    void update(User user) throws DataNotFoundDaoException;
    User findByUsername(String username) throws DataNotFoundDaoException;
    User findById(long id) throws DataNotFoundDaoException;
}
