package org.youcode.hunters_leagues.service.implementations;

import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Hunt;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.repository.HuntRepository;
import org.youcode.hunters_leagues.repository.SpeciesRepository;
import org.youcode.hunters_leagues.service.HuntService;
import org.youcode.hunters_leagues.service.dto.HuntDto;
import org.youcode.hunters_leagues.web.exception.species.InvalidWheightExeption;


@Service
public class HuntServiceImpl implements HuntService {
    private final HuntRepository huntRepository;
    private final SpeciesServiceImpl speciesServiceImp;
    private final ParticipationServiceImpl participationServiceImp;
    public HuntServiceImpl(HuntRepository huntRepository, SpeciesServiceImpl speciesServiceImp, ParticipationServiceImpl participationServiceImp) {
        this.huntRepository = huntRepository;
        this.speciesServiceImp = speciesServiceImp;
        this.participationServiceImp = participationServiceImp;
    }
    @Override
    public Hunt save(HuntDto huntDto) {
        Species species = speciesServiceImp.findById(huntDto.getSpecies_id());
        if (huntDto.getWeight()< species.getMinimumWeight()) {
            throw  new InvalidWheightExeption("");
        }
        Participation participation = participationServiceImp.findById(huntDto.getParticipation_id());
        Hunt hunt = new Hunt();
        hunt.setSpecies(species);
        hunt.setParticipation(participation);
        hunt.setWeight(huntDto.getWeight());
       Hunt savedHint = huntRepository.save(hunt);
        participationServiceImp.calculateScore(savedHint.getParticipation().getId());
return savedHint;
    }
}
