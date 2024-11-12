package org.youcode.hunters_leagues.web.api.hunt;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Hunt;
import org.youcode.hunters_leagues.service.dto.HuntDto;
import org.youcode.hunters_leagues.service.implementations.CompetitionServiceImpl;
import org.youcode.hunters_leagues.service.implementations.HuntServiceImpl;
import org.youcode.hunters_leagues.web.vm.CompetitionResponseVm;
import org.youcode.hunters_leagues.web.vm.CompetitionVm;
import org.youcode.hunters_leagues.web.vm.HuntResponseVm;
import org.youcode.hunters_leagues.web.vm.mapper.CompetitionVmMapper;
import org.youcode.hunters_leagues.web.vm.mapper.HuntVmMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hunts")
public class HuntController {

    private final HuntServiceImpl huntServiceImp;
    private final HuntVmMapper huntVmMapper;

    public HuntController(HuntServiceImpl huntServiceImp, HuntVmMapper huntVmMapper) {
        this.huntServiceImp = huntServiceImp;
        this.huntVmMapper = huntVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addHunt(@RequestBody @Valid HuntDto huntDto){
        Hunt CreatedHunt = huntServiceImp.save(huntDto);
        HuntResponseVm huntResponseVm = huntVmMapper.toVM(CreatedHunt);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hunt created successfully");
        response.put("hunt", huntResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }


//    @PutMapping("update/{id}")
//    public ResponseEntity<Map<String, Object>> updateCompetition(@PathVariable UUID id, @RequestBody @Valid CompetitionVm competitionVm) {
//        Competition competition = competitionVmMapper.toEntity(competitionVm);
//        competition.setId(id);
//        Competition updatedCompetition = competitionServiceImp.update(competition);
//        CompetitionResponseVm competitionResponseVm = competitionVmMapper.toVM(updatedCompetition);
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Competition updated successfully");
//        response.put("Competition", competitionResponseVm);
//        return new  ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteSpecies(@PathVariable UUID id) {
//      competitionServiceImp.delete(id);
//     return ResponseEntity.ok("Competition deleted successfully");
//    }
//
//
//    @GetMapping("id")
//    public ResponseEntity<CompetitionResponseVm> findCompetitionDetails(@RequestParam("id") UUID id){
//        Competition competition = competitionServiceImp.getCompetitionDetails(id);
//        CompetitionResponseVm competitionResponseVm = competitionVmMapper.toVM(competition);
//        return new ResponseEntity<>(competitionResponseVm, HttpStatus.OK);
//    }
//    @GetMapping("all")
//    public ResponseEntity<Page<CompetitionResponseVm>> getAllCompetitions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
//        Page<Competition> competitionsPage = competitionServiceImp.getAllCompetitionPaginated(page, size);
//        List<CompetitionResponseVm> competitionResponseVms = competitionsPage.getContent().stream().map(competitionVmMapper::toVM).toList();
//        Page<CompetitionResponseVm> competitionResponseVmsPage = new PageImpl<>(competitionResponseVms, competitionsPage.getPageable(), competitionsPage.getTotalElements());
//        return ResponseEntity.ok(competitionResponseVmsPage);
//    }
}
