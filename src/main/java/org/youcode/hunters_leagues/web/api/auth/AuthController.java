package org.youcode.hunters_leagues.web.api.auth;

import lombok.RequiredArgsConstructor;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.domain.enums.Role;
import org.youcode.hunters_leagues.dto.AppUserDto;
import org.youcode.hunters_leagues.dto.KeycloakLoginRequest;
import org.youcode.hunters_leagues.dto.TokenResponse;
import org.youcode.hunters_leagues.repository.UserRepository;
import org.youcode.hunters_leagues.service.implementations.KeycloakAuthService;
import org.youcode.hunters_leagues.web.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KeycloakAuthService keycloakAuthService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AppUserDto userDto) {
        // Register in Keycloak first
        keycloakAuthService.registerUser(userDto);

        // Create in our database
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword("KEYCLOAK_MANAGED");
        user.setRole(Role.MEMBER);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCin(userDto.getCin());
        user.setNationality(userDto.getNationality());
        user.setIsActive(true);
        user.setJoinDate(LocalDateTime.now());
        user.setLicenseExpirationDate(userDto.getLicenseExpirationDate());

        userRepository.save(user);

        // Return tokens
        TokenResponse token = keycloakAuthService.authenticate(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid KeycloakLoginRequest request) {
        // Verify user exists in our database
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceNotFoundException("User not found. Please register first.");
        }

        // Get token from Keycloak
        TokenResponse token = keycloakAuthService.authenticate(request.getUsername(), request.getPassword());

        // Update last login time
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok(token);
    }
}