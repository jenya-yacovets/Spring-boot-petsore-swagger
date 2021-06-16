package by.tms.swager.repository;

import by.tms.swager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findTokenByUserId(long id);
}
