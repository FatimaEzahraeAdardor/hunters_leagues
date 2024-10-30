package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Competition;

import java.util.UUID;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
}
