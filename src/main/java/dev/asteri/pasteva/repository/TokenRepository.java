package dev.asteri.pasteva.repository;

import dev.asteri.pasteva.model.Token;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByRefreshToken(String refreshToken);

    @Query("SELECT t FROM Token t JOIN t.user u " +
            "WHERE t.user.id = :userId AND t.isLoggedOut = false")
    List<Token> findAllAccessTokenByUser(@Param("userId") @NotNull UUID userId);

}
