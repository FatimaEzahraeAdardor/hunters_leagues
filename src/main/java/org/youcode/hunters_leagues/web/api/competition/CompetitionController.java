package org.youcode.hunters_leagues.web.api.competition;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.Competition;

import org.youcode.hunters_leagues.service.implementations.CompetitionServiceImpl;
import org.youcode.hunters_leagues.web.vm.CompetitionResponseVm;
import org.youcode.hunters_leagues.web.vm.CompetitionVm;

import org.youcode.hunters_leagues.web.vm.mapper.CompetitionVmMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/competitions")
@Validated
public class CompetitionController {

    private final CompetitionServiceImpl competitionServiceImp;
    private final CompetitionVmMapper competitionVmMapper;

    public CompetitionController(CompetitionServiceImpl competitionServiceImp,CompetitionVmMapper competitionVmMapper) {
        this.competitionServiceImp = competitionServiceImp;
        this.competitionVmMapper = competitionVmMapper;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<Map<String, Object>> addCompetition(@RequestBody @Valid CompetitionVm competitionVm){
        Competition competition = competitionVmMapper.toEntity(competitionVm);
        Competition createdCompetition = competitionServiceImp.save(competition);
        CompetitionResponseVm competitionResponseVm = competitionVmMapper.toVM(createdCompetition);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Competition created successfully");
        response.put("competition", competitionResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<Map<String, Object>> updateCompetition(@PathVariable UUID id, @RequestBody @Valid CompetitionVm competitionVm) {
        Competition competition = competitionVmMapper.toEntity(competitionVm);
        competition.setId(id);
        Competition updatedCompetition = competitionServiceImp.update(competition);
        CompetitionResponseVm competitionResponseVm = competitionVmMapper.toVM(updatedCompetition);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Competition updated successfully");
        response.put("Competition", competitionResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_COMPETITIONS')")
    public ResponseEntity<String> deleteSpecies(@PathVariable UUID id) {
      competitionServiceImp.delete(id);
     return ResponseEntity.ok("Competition deleted successfully");
    }


    @GetMapping("id")
    @PreAuthorize("hasAnyAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<CompetitionResponseVm> findCompetitionDetails(@RequestParam("id") UUID id){
        Competition competition = competitionServiceImp.getCompetitionDetails(id);
        CompetitionResponseVm competitionResponseVm = competitionVmMapper.toVM(competition);
        return new ResponseEntity<>(competitionResponseVm, HttpStatus.OK);
    }
    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<Page<CompetitionResponseVm>> getAllCompetitions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Page<Competition> competitionsPage = competitionServiceImp.getAllCompetitionPaginated(page, size);
        List<CompetitionResponseVm> competitionResponseVms = competitionsPage.getContent().stream().map(competitionVmMapper::toVM).toList();
        Page<CompetitionResponseVm> competitionResponseVmsPage = new PageImpl<>(competitionResponseVms, competitionsPage.getPageable(), competitionsPage.getTotalElements());
        return ResponseEntity.ok(competitionResponseVmsPage);
    }
}
