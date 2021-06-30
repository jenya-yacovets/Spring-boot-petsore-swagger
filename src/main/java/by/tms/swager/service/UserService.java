package by.tms.swager.service;

import by.tms.swager.entity.Token;
import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.exception.InvalidTokenException;
import by.tms.swager.repository.TokenRepository;
import by.tms.swager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
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
            log.warn("Create account. Login is busy | login: " + user.getUsername());
            throw new LoginIsBusyException();
        }

        userRepository.save(user);
        log.info("Create account. Success | User: " + user);
    }

    public void userCreate(User[] users) throws LoginIsBusyException {
        for (User user : users) {
            userCreate(user);
        }
    }

    public void userDelete(String username) throws UserNotFoundException {
        if(!userRepository.existsUserByUsername(username)) {
            log.warn("Delete user. Invalid username | username: " + username);
            throw new UserNotFoundException();
        }
        userRepository.deleteUserByUsername(username);
        log.info("Delete user. Success | login: " + username);
    }

    public void userUpdate(String username, User user) throws UserNotFoundException {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if(userByUsername.isEmpty()) {
            log.warn("Update user. Invalid username | username: " + username);
            throw new UserNotFoundException();
        }

        user.setId(userByUsername.get().getId());
        user.setUsername(userByUsername.get().getUsername());
        userRepository.save(user);
        log.info("Update user. Success | User: " + user);
    }

    public User getUser(String username) throws UserNotFoundException {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername.isPresent()) {
            log.info("Get user. Success | User: " + userByUsername.get());
            return userByUsername.get();
        }
        log.warn("Get user. Invalid username | username: " + username);
        throw new UserNotFoundException();
    }

    public String loginUser(String username, String password) throws UserNotFoundException {
        System.out.println(username);
        System.out.println(password);
        Optional<User> userByUsernameAndPassword = userRepository.findUserByUsernameAndPassword(username, password);
        if(userByUsernameAndPassword.isEmpty()) {
            log.warn("Login user. invalid username or password | username: " + username + ", password: " + password);
            throw new UserNotFoundException();
        }

        Optional<Token> findToken = tokenRepository.findTokenByUserId(userByUsernameAndPassword.get().getId());
        if(findToken.isPresent()) {
            String token = findToken.get().getId().toString();
            log.info("Login user. Success | user: " + userByUsernameAndPassword.get() + ". Get token: " + token);
            return token;
        } else {
            Token newToken = tokenRepository.save(new Token(userByUsernameAndPassword.get()));
            log.info("Login user. Success | user: " + userByUsernameAndPassword.get() + ". Create new token: " + newToken.getId().toString());
            return newToken.getId().toString();
        }
    }

    public void logoutUser(UUID token) throws InvalidTokenException {
        if (!tokenRepository.existsTokenById(token)) {
            log.warn("Logout user. Invalid token | token: " + token);
            throw new InvalidTokenException();
        }

        tokenRepository.deleteById(token);
        log.info("Logout user. Success | token: " + token);
    }

    public User checkAuthorizationUser(UUID token) throws UserNotFoundException {
        Optional<Token> findToken = tokenRepository.findById(token);
        if (findToken.isPresent()) {
            log.debug("Check authorization. Success | user: " + findToken.get().getUser() + ", token: " + token);
            return findToken.get().getUser();
        }

        log.warn("Check authorization. Invalid token | token: " + token);
        throw new UserNotFoundException();
    }
}
