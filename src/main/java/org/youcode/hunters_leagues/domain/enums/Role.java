package org.youcode.hunters_leagues.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of( Permission.CAN_PARTICIPATE,
                    Permission.CAN_VIEW_RANKINGS,
                    Permission.CAN_VIEW_COMPETITIONS,
                    Permission. CAN_SCORE,
                    Permission.CAN_MANAGE_COMPETITIONS,
                    Permission.CAN_MANAGE_USERS,
                    Permission.CAN_MANAGE_SPECIES,
                    Permission.CAN_MANAGE_SETTINGS
            )
    ),

    MEMBER(
            Set.of( Permission.CAN_PARTICIPATE,
                    Permission.CAN_VIEW_RANKINGS,
                    Permission.CAN_VIEW_COMPETITIONS
            )
    ),

    JURY(
            Set.of( Permission. CAN_SCORE,
                    Permission.CAN_MANAGE_COMPETITIONS,
                    Permission.CAN_VIEW_COMPETITIONS
            )
    );



    @Getter
    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        var authorities = getPermissions()
                .stream().map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;

    }

}
