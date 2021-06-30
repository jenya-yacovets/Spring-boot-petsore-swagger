package by.tms.swager.service;

import by.tms.swager.entity.Token;
import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.repository.TokenRepository;
import by.tms.swager.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private User user;

    @BeforeEach
    void createUser() {
        user = new User(0, "Username", "TestFirstName", "TestLastName", "test@test.com", "12345678910", "+384745663", 1);
    }

    @Order(1)
    @Test
    @DisplayName("Create new user")
    void userCreate() throws Exception {
        userService.userCreate(user);
        User getUser = userRepository.findUserByUsername(user.getUsername()).orElseThrow(Exception::new);

        assertEquals(user, getUser);
    }

    @Order(2)
    @Test
    @DisplayName("Checking for duplicate username")
    void checkingDuplicationUsername() {
        assertThrows(LoginIsBusyException.class, () -> userService.userCreate(user));
    }

    @Order(3)
    @Test
    @DisplayName("Create list users")
    void createUserList() throws Exception {
        User[] userList = {
                new User(0, "Username2", "TestFirstName", "TestLastName", "test@test.com", "12345678910", "+384745663", 1),
                new User(0, "Username3", "TestFirstName", "TestLastName", "test@test.com", "12345678910", "+384745663", 1),
                new User(0, "Username4", "TestFirstName", "TestLastName", "test@test.com", "12345678910", "+384745663", 1)
        };
        userService.userCreate(userList);

        List<User> users = userRepository.findAllByUsernameIn(Arrays.asList("Username2", "Username3", "Username4")).orElseThrow(Exception::new);
        assertEquals(3, users.size());
    }

    @Order(4)
    @Test
    @DisplayName("Login user")
    void loginUser() throws Exception {
        String token = userService.loginUser(user.getUsername(), user.getPassword());
        Token findToken = tokenRepository.findById(UUID.fromString(token)).orElseThrow(Exception::new);
        assertEquals(findToken.getId().toString(), token);
    }

    @Order(5)
    @Test
    @DisplayName("Update user")
    void updateUser() throws Exception {
        user.setFirstName("TestFirstNameUpdate");
        user.setLastName("TestLastNameUpdate");
        user.setEmail("test.update@test.com");
        user.setPassword("99999999");
        user.setPhone("+384745663999");
        userService.userUpdate(user.getUsername(), user);

        User findUser = userRepository.findUserByUsername(user.getUsername()).orElseThrow(Exception::new);
        System.out.println(findUser);
        assertEquals(user.getFirstName(), findUser.getFirstName());
        assertEquals(user.getLastName(), findUser.getLastName());
        assertEquals(user.getEmail(), findUser.getEmail());
        assertEquals(user.getPassword(), findUser.getPassword());
        assertEquals(user.getPhone(), findUser.getPhone());
    }

    @Order(6)
    @Test
    @DisplayName("Get user")
    void getUser() throws UserNotFoundException {
        User findUser = userService.getUser(user.getUsername());
        assertEquals(user, findUser);
    }
}
