package org.youcode.hunters_leagues.service.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.repository.CompetitionRepository;
import org.youcode.hunters_leagues.service.CompetitionService;
import org.youcode.hunters_leagues.web.exception.competition.InvalidCompetitionExeption;

import java.util.Optional;
import java.util.UUID;
@Service
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;
    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
    @Override
    public Competition save(Competition competition) {
        if (competition.getId() != null) {
            Competition competitionFound = competitionRepository.findById(competition.getId()).orElse(null);
            if (competitionFound == null) {
                throw new InvalidCompetitionExeption("Competition not found");
            }
        }
        return competitionRepository.save(competition);
    }

    @Override
    public Competition update(Competition competition) {
        Competition competitionFound = competitionRepository.findById(competition.getId()).orElse(null);
        if (competitionFound == null) {
            throw new InvalidCompetitionExeption("Competition not found");
        }
         competition.setCode(competition.getCode());
        competition.setDate(competition.getDate());
        competition.setLocation(competition.getLocation());
        competition.setMinParticipants(competition.getMinParticipants());
        competition.setMaxParticipants(competition.getMaxParticipants());
        competition.setSpeciesType(competition.getSpeciesType());
        competition.setOpenRegistration(competition.getOpenRegistration());
        return competitionRepository.save(competition);
    }

    @Override
    public Boolean delete(UUID id) {
            Competition competition = competitionRepository.findById(id).orElse(null);
            if (competition == null) {
                throw new InvalidCompetitionExeption("Competition not found");
            }
            competitionRepository.delete(competition);
            return true;
    }

    @Override
    public Page<Competition> getAllCompetitionPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return competitionRepository.findAll(pageable);

    }

    @Override
    public Competition getCompetitionDetails(UUID id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new InvalidCompetitionExeption("Competition with ID " + id + " not found."));
    }
}
