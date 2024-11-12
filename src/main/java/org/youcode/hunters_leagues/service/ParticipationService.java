package org.youcode.hunters_leagues.service;

import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.web.vm.ParticipationVm;

import java.util.UUID;


public interface ParticipationService {
    Participation save(ParticipationVm participationVm);
    Participation findById(UUID id);
}
