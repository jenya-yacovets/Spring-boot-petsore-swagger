package by.tms.swager.store.user;

import by.tms.swager.entity.User;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import by.tms.swager.exception.dao.DuplicationDataDaoException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserDao implements UserDao{

    private final List<User> list = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void save(User user) throws DuplicationDataDaoException {
        try {
            findByUsername(user.getUsername());
            throw new DuplicationDataDaoException("Login is busy");
        } catch (DataNotFoundDaoException e) {
            user.setId(nextId++);
            list.add(user);
        }
    }

    @Override
    public void delete(long id) throws DataNotFoundDaoException {
        User findUser = findById(id);
        list.remove(findUser);
    }

    @Override
    public void delete(String username) throws DataNotFoundDaoException {
        User findUser = findByUsername(username);
        list.remove(findUser);
    }

    @Override
    public void update(User user) throws DataNotFoundDaoException {
        User findUser = findByUsername(user.getUsername());
        int i = list.indexOf(findUser);
        list.add(i, user);
    }

    @Override
    public User findByUsername(String username) throws DataNotFoundDaoException {
        User findUser = list.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
        if(findUser != null) {
            return findUser;
        } else {
            throw new DataNotFoundDaoException("User not found");
        }
    }

    @Override
    public User findById(long id) throws DataNotFoundDaoException {
        User findUser = list.stream().filter(user -> user.getId() == id).findAny().orElse(null);
        if(findUser != null) {
            return findUser;
        } else {
            throw new DataNotFoundDaoException("User not found");
        }
    }
}
