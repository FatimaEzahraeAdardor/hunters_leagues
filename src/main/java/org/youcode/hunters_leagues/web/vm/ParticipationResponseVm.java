package org.youcode.hunters_leagues.web.vm;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class ParticipationResponseVm {
    private UserResponseVm user;
    private CompetitionResponseVm competitionId;
    private double score;
}
