package org.youcode.hunters_leagues.web.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricalParticipationVm {
    private CompetitionResponseVm competition;
    private double score;
}
