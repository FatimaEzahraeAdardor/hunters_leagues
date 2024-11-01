package org.youcode.hunters_leagues.service.implementations;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.domain.enums.Role;
import org.youcode.hunters_leagues.repository.UserRepository;
import org.youcode.hunters_leagues.service.UserService;
import org.youcode.hunters_leagues.web.exception.user.InvalidCredentialsException;
import org.youcode.hunters_leagues.web.exception.user.InvalidUserExeption;
import org.youcode.hunters_leagues.web.exception.user.UserAlreadyExistException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        user.setRole(Role.MEMBER);
        user.setJoinDate(LocalDateTime.now());
        user.setLicenseExpirationDate(LocalDateTime.now().plusMonths(1));
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

    @Override
    public User update(User user) {
        User userFound = userRepository.findById(user.getId()).orElse(null);
        if (userFound == null) {
            throw new InvalidUserExeption("User not found");
        }
        userFound.setUsername(user.getUsername() != null ? user.getUsername() : userFound.getUsername());
        userFound.setFirstName(user.getFirstName() != null ? user.getFirstName() : userFound.getFirstName());
        userFound.setLastName(user.getLastName() != null ? user.getLastName() : userFound.getLastName());
        userFound.setEmail(user.getEmail() != null ? user.getEmail() : userFound.getEmail());
        if (user.getPassword() != null) {
            userFound.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        }else {
            userFound.setPassword(userFound.getPassword());
        }
        userFound.setRole(user.getRole() != null ? user.getRole() : userFound.getRole());
        userFound.setJoinDate(user.getJoinDate() != null ? user.getJoinDate() : userFound.getJoinDate());
        userFound.setNationality(user.getNationality() != null ? user.getNationality() : userFound.getNationality());
        user.setCin(user.getCin() != null ? user.getCin() : userFound.getCin());
        user.setLicenseExpirationDate(user.getLicenseExpirationDate() != null ? user.getLicenseExpirationDate() : userFound.getLicenseExpirationDate());
        return userRepository.save(userFound);
    }

    @Override
    public Boolean delete(UUID id) {
        User userFound = userRepository.findById(id).orElse(null);
        if (userFound == null) {
            throw new InvalidUserExeption("User not found");
        }
        userRepository.delete(userFound);
        return true;
    }

    @Override
    public List<User> findByUsernameOrEmail(String keyWord) {
        return userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(keyWord, keyWord);
    }


}
