package org.youcode.hunters_leagues.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;

import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species, UUID> {
    Page<Species> findByCategory(SpeciesType category, Pageable pageable);

}
