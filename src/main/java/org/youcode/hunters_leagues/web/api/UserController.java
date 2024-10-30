package org.youcode.hunters_leagues.web.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.service.UserService;
import org.youcode.hunters_leagues.service.implementations.UserServiceImpl;
import org.youcode.hunters_leagues.web.vm.SignUpVm;
import org.youcode.hunters_leagues.web.vm.mapper.SignUpVmMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final SignUpVmMapper signUpVmMapper;


    public UserController(UserServiceImpl userServiceImpl, SignUpVmMapper signUpVmMapper) {
        this.userServiceImpl = userServiceImpl;
        this.signUpVmMapper = signUpVmMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody @Valid SignUpVm signUpVm){
        User user = signUpVmMapper.ToUser(signUpVm);
        userServiceImpl.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
