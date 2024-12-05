package org.youcode.hunters_leagues.repository;

import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String name, String email);
    Optional<User> findByEmail(String email);

//    Page<User> findAll(Pageable pageable);


}
