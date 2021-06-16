package by.tms.swager.service;

import by.tms.swager.entity.Token;
import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.exception.InvalidTokenException;
import by.tms.swager.repository.TokenRepository;
import by.tms.swager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public void userCreate(User user) throws LoginIsBusyException {
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());

        if(userByUsername.isPresent()) {
            throw new LoginIsBusyException();
        }

        userRepository.save(user);
    }

    public void userCreate(User[] users) {
        for (User user : users) {
            userRepository.save(user);
        }
    }

    public void userDelete(String username) throws UserNotFoundException {
        userRepository.deleteUserByUsername(username);
    }

    public void userUpdate(String username, User user) throws UserNotFoundException {
        long findId = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new).getId();
        user.setId(findId);
        userRepository.save(user);
    }

    public User getUser(String username) throws UserNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public String loginUser(String username, String password) throws UserNotFoundException {
        User findUser = userRepository.findUserByUsernameAndPassword(username, password).orElseThrow(UserNotFoundException::new);
        Optional<Token> findToken = tokenRepository.findTokenByUserId(findUser.getId());

        if(findToken.isPresent()) {
            return findToken.get().getId().toString();
        } else {
            Token newToken = new Token(findUser);
            newToken = tokenRepository.save(newToken);
            return newToken.getId().toString();
        }
    }

    public void logoutUser(UUID token) throws UserNotFoundException, InvalidTokenException {
        checkAuthorizationUser(token);
        tokenRepository.deleteById(token);
    }

    public User checkAuthorizationUser(UUID token) throws UserNotFoundException {
        return tokenRepository.findById(token).orElseThrow(UserNotFoundException::new).getUser();
    }
}
