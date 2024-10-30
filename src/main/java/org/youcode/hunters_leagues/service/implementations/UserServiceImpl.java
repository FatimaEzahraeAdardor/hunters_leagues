package org.youcode.hunters_leagues.service.implementations;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.repository.UserRepository;
import org.youcode.hunters_leagues.service.UserService;
import org.youcode.hunters_leagues.web.exception.UserAlreadyExistException;

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
//        Optional<User> userOptional = this.findByUserNameOrImail(user.getUsername(), user.getEmail());
//        if (userOptional.isPresent()) {
//            throw new UserAlreadyExistException("User already exists");
//        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserNameOrImail(String username, String email) {
        return null;
    }


}
