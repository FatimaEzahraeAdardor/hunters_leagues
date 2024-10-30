package org.youcode.hunters_leagues.web.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInVm {
    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

}
