package org.youcode.hunters_leagues.web.api.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.service.implementations.UserServiceImpl;
import org.youcode.hunters_leagues.web.vm.SignInVm;
import org.youcode.hunters_leagues.web.vm.SignUpVm;
import org.youcode.hunters_leagues.web.vm.mapper.SignInVmMapper;
import org.youcode.hunters_leagues.web.vm.mapper.SignUpVmMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final SignUpVmMapper signUpVmMapper;
    private final SignInVmMapper signInVmMapper;


    public UserController(UserServiceImpl userServiceImpl, SignUpVmMapper signUpVmMapper, SignInVmMapper signInVmMapper) {
        this.userServiceImpl = userServiceImpl;
        this.signUpVmMapper = signUpVmMapper;
        this.signInVmMapper = signInVmMapper;
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

}
