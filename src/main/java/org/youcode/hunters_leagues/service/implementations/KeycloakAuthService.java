package org.youcode.hunters_leagues.service.implementations;

import lombok.RequiredArgsConstructor;
import org.youcode.hunters_leagues.dto.AppUserDto;
import org.youcode.hunters_leagues.dto.TokenResponse;
import org.youcode.hunters_leagues.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAuthService {
    private final RestTemplate restTemplate;


    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.client-secret}")
    private String adminClientSecret;

    public TokenResponse authenticate(String username, String password) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, realm);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("username", username);
        map.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            return restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
        } catch (Exception e) {
            throw new BadRequestException("Authentication failed: " + e.getMessage());
        }
    }

    public void registerUser(AppUserDto userDto) {
        String userApiUrl = String.format("%s/admin/realms/%s/users", authServerUrl, realm);

        Map<String, Object> user = new HashMap<>();
        user.put("username", userDto.getUsername());
        user.put("email", userDto.getEmail());
        user.put("firstName", userDto.getFirstName());
        user.put("lastName", userDto.getLastName());
        user.put("enabled", true);

        // Set password
        List<Map<String, Object>> credentials = new ArrayList<>();
        Map<String, Object> pwd = new HashMap<>();
        pwd.put("type", "password");
        pwd.put("value", userDto.getPassword());
        pwd.put("temporary", false);
        credentials.add(pwd);
        user.put("credentials", credentials);

        // Set roles - modify this according to your Keycloak realm roles
        List<String> roles = new ArrayList<>();
        roles.add("MEMBER");  // Make sure this role exists in your Keycloak realm
        user.put("realmRoles", roles);  // Changed from List<Map> to List<String>

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Add debugging to see what's being sent
        System.out.println("Sending to Keycloak: " + user);
        System.out.println("Token: " + getAdminToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(userApiUrl, request, String.class);  // Changed to String.class to see error response
            System.out.println("Response: " + response.getBody());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BadRequestException("Failed to register user in Keycloak");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error response: " + e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new BadRequestException("Username or email already exists in Keycloak");
            }
            throw new BadRequestException("Failed to register user: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Unexpected error during registration: " + e.getMessage());
        }
    }

    @PostMapping("/test-admin-token")
    public String testAdminToken() {
        return getAdminToken();
    }

    private String getAdminToken() {
        String tokenUrl = String.format("%s/realms/master/protocol/openid-connect/token", authServerUrl);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", adminClientId);
        map.add("client_secret", adminClientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            TokenResponse response = restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
            return response.getAccess_token();
        } catch (Exception e) {
            throw new BadRequestException("Failed to get admin token: " + e.getMessage());
        }
    }
}
