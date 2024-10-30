package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Participation;

import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
}
