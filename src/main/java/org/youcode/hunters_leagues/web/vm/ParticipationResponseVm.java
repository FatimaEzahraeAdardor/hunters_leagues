package org.youcode.hunters_leagues.web.vm;

import lombok.*;


@Getter
@Setter
public class ParticipationResponseVm {
    private UserResponseVm user;
    private CompetitionResponseVm competition;
    private double score;
}
