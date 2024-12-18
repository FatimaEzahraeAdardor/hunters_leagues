package org.youcode.hunters_leagues.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.youcode.hunters_leagues.filter.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.youcode.hunters_leagues.domain.enums.Permission.*;
import static org.youcode.hunters_leagues.domain.enums.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/**").hasAnyRole(ADMIN.name())
                .requestMatchers("api/species/**").hasAnyRole(ADMIN.name())
                .requestMatchers(GET,"/api/participation/**").hasAuthority(CAN_VIEW_RANKINGS.name())
                .requestMatchers(POST,"/api/participation/**").hasAuthority(CAN_PARTICIPATE.name())
                .requestMatchers("/api/hunts").hasAnyRole(ADMIN.name(), JURY.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
