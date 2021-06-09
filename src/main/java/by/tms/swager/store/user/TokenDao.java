package by.tms.swager.store.user;

import by.tms.swager.entity.User;
import by.tms.swager.exception.dao.DataNotFoundDaoException;

public interface TokenDao {
    String createUserToken(User user);
    User getUserByToken(String token) throws DataNotFoundDaoException;
    void deleteToken(String token);
}
