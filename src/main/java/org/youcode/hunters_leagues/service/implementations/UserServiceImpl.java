package org.youcode.hunters_leagues.service.implementations;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.repository.UserRepository;
import org.youcode.hunters_leagues.service.UserService;
import org.youcode.hunters_leagues.web.exception.user.InvalidCredentialsException;
import org.youcode.hunters_leagues.web.exception.user.InvalidUserExeption;
import org.youcode.hunters_leagues.web.exception.user.UserAlreadyExistException;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if (user==null){
            throw new InvalidUserExeption("user is null");
        }
        Optional<User> userOptional = this.findByUserName(user.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public Optional<User> login(User user) {
        if (user == null) {
            throw new InvalidUserExeption("User is null");
        }
        Optional<User> userOptional = this.findByUserName(user.getUsername());
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Username or password is incorrect");
        }
        User foundUser = userOptional.get();
        if (!BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            throw new InvalidCredentialsException("Username or password is incorrect");
        }

        return userOptional;
    }


}
