package org.youcode.hunters_leagues.service;

import org.youcode.hunters_leagues.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User User);

    Optional<User> findByUserName(String username);

     Optional<User> login(User user);
     User update(User user);
     Boolean delete(UUID id);
    List<User> findByUsernameOrEmail(String keyWord);

}
