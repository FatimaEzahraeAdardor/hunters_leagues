package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.domain.User;

import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    Boolean existsByUserAndCompetition(User user, Competition competition);

}
