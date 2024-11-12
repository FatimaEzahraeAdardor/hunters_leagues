package org.youcode.hunters_leagues.service.implementations;

import org.springframework.stereotype.Service;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.repository.CompetitionRepository;
import org.youcode.hunters_leagues.repository.ParticipationRepository;
import org.youcode.hunters_leagues.repository.UserRepository;
import org.youcode.hunters_leagues.service.ParticipationService;
import org.youcode.hunters_leagues.web.exception.competition.CompetitionDateException;
import org.youcode.hunters_leagues.web.exception.competition.InvalidCompetitionExeption;
import org.youcode.hunters_leagues.web.exception.competition.InvalidRegistrationException;
import org.youcode.hunters_leagues.web.exception.participation.ParticipationAlreadyExistsException;
import org.youcode.hunters_leagues.web.exception.participation.ParticipationInvalidException;
import org.youcode.hunters_leagues.web.exception.user.InvalidUserExeption;
import org.youcode.hunters_leagues.web.exception.user.UserLicenseExpirationException;
import org.youcode.hunters_leagues.web.vm.ParticipationVm;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipationServiceImpl implements ParticipationService {
    private ParticipationRepository participationRepository;
    private CompetitionRepository competitionRepository;
    private UserRepository userRepository;
    public ParticipationServiceImpl(ParticipationRepository participationRepository, CompetitionRepository competitionRepository, UserRepository userRepository) {
        this.participationRepository = participationRepository;
        this.competitionRepository = competitionRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Participation save(ParticipationVm participationVm) {
        User user = userRepository.findById(participationVm.getUserId())
                .orElseThrow(() -> new InvalidUserExeption("User not found"));

        Competition competition = competitionRepository.findById(participationVm.getCompetitionId())
                .orElseThrow(() -> new InvalidCompetitionExeption("Competition not found"));

        if (user.getLicenseExpirationDate().isBefore(LocalDateTime.now())) {
            throw new UserLicenseExpirationException("User license has expired.");
        }

        if (competition.getDate().isBefore(LocalDateTime.now())) {
            throw new CompetitionDateException("Competition date has already passed.");
        }

        if (!competition.getOpenRegistration()) {
            throw new InvalidRegistrationException("Registration is closed.");
        }

        if (competition.getParticipations().size() >= competition.getMaxParticipants()) {
            throw new ParticipationInvalidException("Competition is full.");
        }

        if (participationRepository.existsByUserAndCompetition(user, competition)) {
            throw new ParticipationAlreadyExistsException("User is already registered for this competition.");
        }

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setCompetition(competition);
        participation.setScore(participationVm.getScore());

        return participationRepository.save(participation);
    }

    @Override
    public Participation findById(UUID id) {
        Optional<Participation> participationOptional = participationRepository.findById(id);
        Participation participation = participationOptional.get();
        return participation;

    }
    public Double calculateScore(UUID id) {
        Participation participation = findById(id);
        Double score = participation.getHunts().stream().mapToDouble(hunt -> hunt.getSpecies().getPoints() + hunt.getWeight() * hunt.getSpecies().getCategory().getValue() * hunt.getSpecies().getDifficulty().getValue()).sum();
        participation.setScore(score);
        participationRepository.save(participation);
        return score;
    }


}
