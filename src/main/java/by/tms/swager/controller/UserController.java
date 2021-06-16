package by.tms.swager.controller;

import by.tms.swager.entity.User;
import by.tms.swager.exception.LoginIsBusyException;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.exception.InvalidTokenException;
import by.tms.swager.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> userCreate(@Valid @RequestBody User user) {
        try {
            userService.userCreate(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (LoginIsBusyException e) {
            return new ResponseEntity<>("Login is busy", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"/createWithArray", "/createWithList"})
    public ResponseEntity<?> userCreateWithArray(@Valid @RequestBody User[] users) {
        userService.userCreate(users);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> userDelete(@PathVariable @Min(4) @Max(20) String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            userService.userDelete(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @PathVariable @Min(4) @Max(20) String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            userService.userUpdate(username, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable @Min(4) @Max(20) String username) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            User user = userService.getUser(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam @Min(4) @Max(20) String username, @RequestParam @Min(7) @Max(35) String password) {
        if(username == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            String token = userService.loginUser(username, password);

            MultiValueMap<String, String> resHeader = new HttpHeaders();
            resHeader.add("X-Expires-After", "0");
            resHeader.add("X-Rate-Limit", "0");

            return new ResponseEntity<>(token, resHeader, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("Invalid login or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("X-api-key") UUID token) {
        try {
            userService.logoutUser(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | InvalidTokenException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
