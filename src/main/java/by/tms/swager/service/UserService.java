package by.tms.swager.service;

import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import by.tms.swager.exception.dao.DuplicationDataDaoException;
import by.tms.swager.store.user.TokenDao;
import by.tms.swager.store.user.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserDao userDao;
    private final TokenDao tokenDao;

    public UserService(UserDao userDao, TokenDao tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    public void userCreate(User user) throws LoginIsBusyException {
        try {
            userDao.save(user);
        } catch (DuplicationDataDaoException e) {
            throw new LoginIsBusyException();
        }
    }

    public void userCreate(User[] users) throws LoginIsBusyException {
        try {
            for (User user : users) {
                userDao.save(user);
            }
        } catch (DuplicationDataDaoException e) {
            throw new LoginIsBusyException();
        }
    }

    public void userDelete(String username) throws UserNotFoundException {
        try {
            userDao.delete(username);
        } catch (DataNotFoundDaoException e) {
           throw new UserNotFoundException();
        }
    }

    public void userUpdate(User user) throws UserNotFoundException {
        try {
            userDao.update(user);
        } catch (DataNotFoundDaoException e) {
            throw new UserNotFoundException();
        }
    }

    public User getUser(String username) throws UserNotFoundException {
        try {
            return userDao.findByUsername(username);
        } catch (DataNotFoundDaoException e) {
            throw new UserNotFoundException();
        }
    }

    public String loginUser(String username, String password) throws UserNotFoundException {
        try {
            User user = userDao.findByUsername(username);

            if(user.getPassword().equals(password)) {
                return tokenDao.createUserToken(user);
            }

            throw new UserNotFoundException();
        } catch (DataNotFoundDaoException e) {
            throw new UserNotFoundException();
        }
    }

    public void logoutUser(String token) {
        tokenDao.deleteToken(token);
    }

    public User checkAuthorizationUser(String token) throws UserNotFoundException {
        try {
            User userByToken = tokenDao.getUserByToken(token);
            return userByToken;
        } catch (DataNotFoundDaoException e) {
            throw new UserNotFoundException();
        }
    }
}
