package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Species;

import java.util.UUID;

public interface SpeicesRepository extends JpaRepository<Species, UUID> {
}
