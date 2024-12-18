package org.youcode.hunters_leagues.web.vm;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationVm {
    @NotNull(message = "User ID is required")
    private UUID userId;
    @NotNull(message = "Competition ID is required")
    private UUID competitionId;
    private double score;
}
