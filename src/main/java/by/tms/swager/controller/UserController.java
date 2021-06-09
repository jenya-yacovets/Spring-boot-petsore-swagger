package by.tms.swager.controller;

import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Void> userCreate(@RequestBody User user) {
        try {
            userService.userCreate(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (LoginIsBusyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping({"/createWithArray", "/createWithList"})
    public ResponseEntity<Void> userCreateWithArray(@RequestBody User[] user) {
        try {
            userService.userCreate(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (LoginIsBusyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> userDelete(@PathVariable String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            userService.userDelete(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        user.setUsername(username);

        try {
            userService.userUpdate(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            User user = userService.getUser(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            String token = userService.loginUser(username, password);

            MultiValueMap<String, String> resHeader = new HttpHeaders();
            resHeader.add("X-Expires-After", "0");
            resHeader.add("X-Rate-Limit", "0");

            return new ResponseEntity<>(token, resHeader, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("X-api-key") String token) {
        userService.logoutUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
