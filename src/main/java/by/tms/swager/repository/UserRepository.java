package by.tms.swager.repository;

import by.tms.swager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
