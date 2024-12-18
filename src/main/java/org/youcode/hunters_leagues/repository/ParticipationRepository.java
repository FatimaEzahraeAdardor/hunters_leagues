package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.domain.User;

import java.util.List;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

    Boolean existsByUserAndCompetition(User user, Competition competition);

    List<Participation> findByUserId(UUID userId);
    List<Participation> findTop3ByCompetitionIdOrderByScoreDesc(UUID competitionId);

    @Query("SELECT p FROM Participation p JOIN FETCH p.competition c " +
            "WHERE p.user.id = :userId AND c.date < CURRENT_TIMESTAMP " +
            "ORDER BY c.date DESC")
    List<Participation> findCompetitionsHistoryByUser(@Param("userId") UUID userId);
}
