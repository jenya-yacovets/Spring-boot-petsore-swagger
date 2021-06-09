package by.tms.swager.store.user;

import by.tms.swager.entity.User;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryTokenDao implements TokenDao {
    private final Map<String, User> map = new HashMap<>();

    @Override
    public String createUserToken(User user) {
        String token = UUID.randomUUID().toString();
        map.put(token, user);
        return token;
    }

    @Override
    public User getUserByToken(String token) throws DataNotFoundDaoException {
        User user = map.get(token);
        if(user == null) throw new DataNotFoundDaoException("Token not found");
        return user;
    }

    @Override
    public void deleteToken(String token) {
        map.remove(token);
    }
}
