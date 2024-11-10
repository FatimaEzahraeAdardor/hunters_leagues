package org.youcode.hunters_leagues.service;

import org.springframework.data.domain.Page;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;

import java.util.UUID;

public interface SpeciesService {
    Species save(Species species);
    Species update(Species species);
    Boolean delete(UUID id);
    Page<Species> getAllSpeciesPaginated(int page, int size);
    Page<Species> getSpeciesByCategory(SpeciesType category, int page, int size);


}
