package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

}