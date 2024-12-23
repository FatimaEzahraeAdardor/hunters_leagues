package org.youcode.hunters_leagues.web.api.participation;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.service.dto.CompetitionResultDto;
import org.youcode.hunters_leagues.service.implementations.ParticipationServiceImpl;
import org.youcode.hunters_leagues.web.vm.*;
import org.youcode.hunters_leagues.web.vm.mapper.ParticipationVmMapper;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participation")
@Validated
public class ParticipationController {

    private final ParticipationServiceImpl participationServiceImp;
    private final ParticipationVmMapper participationVmMapper;

   public ParticipationController(ParticipationServiceImpl participationServiceImp, ParticipationVmMapper participationVmMapper) {
       this.participationServiceImp = participationServiceImp;
       this.participationVmMapper = participationVmMapper;
   }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addParticipation(@RequestBody @Valid ParticipationVm participationVm) {
        Participation createdParticipation = participationServiceImp.save(participationVm);
        ParticipationResponseVm participationResponseVm = participationVmMapper.ToVM(createdParticipation);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Participation created successfully");
        response.put("participation", participationResponseVm);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/results")
    public List<CompetitionResultDto> getUserCompetitionResults(@RequestParam UUID userId) {
        return participationServiceImp.getUserCompetitionResults(userId);
    }
    @GetMapping("/podium")
    public List<ParticipationResponseVm> getPodiumParticipations(@RequestParam UUID competitionId) {
        List<Participation> participations = participationServiceImp.getTop3Participants(competitionId);
        return participations.stream()
                .map(participationVmMapper::ToVM)
                .collect(Collectors.toList());
    }
    @GetMapping("historical")
    public List<HistoricalParticipationVm> getHistoricalParticipations(@RequestParam UUID userId) {
       List<Participation> participations = participationServiceImp.findCompetitionsHistoryByUser(userId);
       return participations.stream()
               .map(participation -> participationVmMapper.ToHISTORICAL_PARTICIPATION_Vm(participation))
               .collect(Collectors.toList());
    }

}
