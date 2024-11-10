package org.youcode.hunters_leagues.service.implementations;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;
import org.youcode.hunters_leagues.repository.SpeciesRepository;
import org.youcode.hunters_leagues.service.SpeciesService;
import org.youcode.hunters_leagues.web.exception.species.InvalidSpeciesExeption;
import org.youcode.hunters_leagues.web.exception.user.InvalidUserExeption;

import java.util.UUID;
@Service
public class SpeciesServiceImpl implements SpeciesService {
    private final SpeciesRepository speciesRepository;
 public SpeciesServiceImpl(SpeciesRepository speicesRepository) {
     this.speciesRepository = speicesRepository;
 }
    @Override
    public Species save(Species species) {
        if (species == null) {
            throw new InvalidSpeciesExeption("Species cannot be null");
        }
        return speciesRepository.save(species);
    }

    @Override
    public Species update(Species species) {
        Species speciesFound = speciesRepository.findById(species.getId()).orElse(null);
        if (speciesFound == null) {
            throw new InvalidSpeciesExeption("Species not found");
        }
        speciesFound.setName(species.getName());
        speciesFound.setCategory(species.getCategory());
        speciesFound.setMinimumWeight(species.getMinimumWeight());
        speciesFound.setDifficulty(species.getDifficulty());
        speciesFound.setPoints(species.getPoints());
        return speciesRepository.save(speciesFound);
    }

    @Override
    public Boolean delete(UUID id) {
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Species not found"));

//        huntRepository.deleteBySpeciesId(speciesId);

        speciesRepository.delete(species);
        return true;
    }

    @Override
    public Page<Species> getAllSpeciesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return speciesRepository.findAll(pageable);
    }

    @Override
    public Page<Species> getSpeciesByCategory(SpeciesType category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return speciesRepository.findByCategory(category, pageable);
    }
}
