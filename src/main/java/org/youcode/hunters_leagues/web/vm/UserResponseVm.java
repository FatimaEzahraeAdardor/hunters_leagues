package org.youcode.hunters_leagues.web.vm;

import lombok.*;
import org.youcode.hunters_leagues.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserResponseVm {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private Role role;

    private String cin;

    private String email;

    private String nationality;

    private LocalDateTime joinDate;

    private LocalDateTime licenseExpirationDate;
}
