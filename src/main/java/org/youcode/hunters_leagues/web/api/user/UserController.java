package org.youcode.hunters_leagues.web.api.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<String> addUser(@RequestBody @Valid SignUpVm signUpVm){
        User user = signUpVmMapper.ToUser(signUpVm);
        userServiceImpl.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid SignInVm signInVm){
        User user = signInVmMapper.ToUser(signInVm);
        userServiceImpl.login(user);
        return ResponseEntity.ok("User logged in successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody @Valid UserVm userVm) {
        User user = userVmMapper.toUser(userVm);
        user.setId(id);
        userServiceImpl.update(user);
        return ResponseEntity.ok("User updated successfully");
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
