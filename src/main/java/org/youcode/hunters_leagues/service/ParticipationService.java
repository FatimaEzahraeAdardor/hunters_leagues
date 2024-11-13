package org.youcode.hunters_leagues.service;

import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.service.dto.CompetitionResultDto;
import org.youcode.hunters_leagues.web.vm.ParticipationVm;

import java.util.List;
import java.util.UUID;


public interface ParticipationService {
    Participation save(ParticipationVm participationVm);

    Participation findById(UUID id);

    List<CompetitionResultDto> getUserCompetitionResults(UUID userId);

    List<Participation> getParticipationByUserId(UUID userId);
    List<Participation> getTop3Participants(UUID competitionId);
    Double calculateScore(UUID id);
}
