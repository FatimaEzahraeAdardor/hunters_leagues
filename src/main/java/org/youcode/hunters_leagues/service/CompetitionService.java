package org.youcode.hunters_leagues.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Species;

import java.util.UUID;

public interface CompetitionService {
    Competition save(Competition competition);
    Competition update(Competition competition);
    Boolean delete(UUID id);
    Page<Competition> getAllCompetitionPaginated(int page, int size);
    Competition getCompetitionDetails(UUID id);

}
