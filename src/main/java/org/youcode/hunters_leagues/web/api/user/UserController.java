package org.youcode.hunters_leagues.web.api.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.service.implementations.UserServiceImpl;
import org.youcode.hunters_leagues.web.vm.SignInVm;
import org.youcode.hunters_leagues.web.vm.SignUpVm;
import org.youcode.hunters_leagues.web.vm.UserResponseVm;
import org.youcode.hunters_leagues.web.vm.UserVm;
import org.youcode.hunters_leagues.web.vm.mapper.SignInVmMapper;
import org.youcode.hunters_leagues.web.vm.mapper.SignUpVmMapper;
import org.youcode.hunters_leagues.web.vm.mapper.UserResponseVmMapper;
import org.youcode.hunters_leagues.web.vm.mapper.UserVmMapper;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final SignUpVmMapper signUpVmMapper;
    private final SignInVmMapper signInVmMapper;
    private final UserVmMapper userVmMapper;
    private final UserResponseVmMapper userResponseVmMapper;


    public UserController(UserServiceImpl userServiceImpl, SignUpVmMapper signUpVmMapper, SignInVmMapper signInVmMapper, UserVmMapper userVmMapper, UserResponseVmMapper userResponseVmMapper) {
        this.userServiceImpl = userServiceImpl;
        this.signUpVmMapper = signUpVmMapper;
        this.signInVmMapper = signInVmMapper;
        this.userVmMapper = userVmMapper;
        this.userResponseVmMapper = userResponseVmMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody @Valid SignUpVm signUpVm){
        User user = signUpVmMapper.ToUser(signUpVm);
        User createdUser = userServiceImpl.save(user);
        UserResponseVm userResponseVm = userResponseVmMapper.toUserVm(createdUser);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registred successfully");
        response.put("user", userResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid SignInVm signInVm){
        User user = signInVmMapper.ToUser(signInVm);
       Optional<User> loggedUser =  userServiceImpl.login(user);
        UserResponseVm userResponseVm = userResponseVmMapper.toUserVm(loggedUser.get());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged successfully");
        response.put("user", userResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable UUID id, @RequestBody @Valid UserVm userVm) {
        User user = userVmMapper.toUser(userVm);
        user.setId(id);
        User updatedUser = userServiceImpl.update(user);
        UserResponseVm userResponseVm = userResponseVmMapper.toUserVm(updatedUser);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("user", userResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
      userServiceImpl.delete(id);
     return ResponseEntity.ok("User deleted successfully");
    }
    @GetMapping("search")
    public ResponseEntity<List<UserResponseVm>> searchUser(@RequestParam String keyword) {
        List<User> userList =userServiceImpl.findByUsernameOrEmail(keyword);
        List<UserResponseVm> userResponseVms = userList.stream().map(userResponseVmMapper::toUserVm).toList();
        return ResponseEntity.ok(userResponseVms);

    }
    @GetMapping("all")
    public ResponseEntity<Page<UserResponseVm>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Page<User> userPage = userServiceImpl.getAllUsersPaginated(page, size);
        List<UserResponseVm> userResponseVms = userPage.getContent().stream().map(userResponseVmMapper::toUserVm).toList();
        Page<UserResponseVm> userResponsePage = new PageImpl<>(userResponseVms, userPage.getPageable(), userPage.getTotalElements());
        return ResponseEntity.ok(userResponsePage);
    }
}
