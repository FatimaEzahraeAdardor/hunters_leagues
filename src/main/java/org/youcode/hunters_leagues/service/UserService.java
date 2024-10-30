package org.youcode.hunters_leagues.service;

import org.youcode.hunters_leagues.domain.User;

import java.util.Optional;

public interface UserService {
    User save(User User);
   Optional<User>  findByUserNameOrImail(String username , String email);

}
