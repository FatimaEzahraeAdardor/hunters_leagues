package org.youcode.hunters_leagues.web.api.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;
import org.youcode.hunters_leagues.repository.SpeciesRepository;
import org.youcode.hunters_leagues.service.implementations.SpeciesServiceImpl;
import org.youcode.hunters_leagues.web.vm.SpeciesResponseVm;
import org.youcode.hunters_leagues.web.vm.SpeciesVm;
import org.youcode.hunters_leagues.web.vm.UserResponseVm;
import org.youcode.hunters_leagues.web.vm.mapper.SpeciesMapper;

import java.util.*;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final SpeciesServiceImpl speciesServiceImp;
    private final SpeciesMapper speciesMapper;


    public SpeciesController(SpeciesServiceImpl speciesServiceImp, SpeciesMapper speciesMapper) {
        this.speciesServiceImp = speciesServiceImp;
        this.speciesMapper = speciesMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addSpecies(@RequestBody @Valid SpeciesVm speciesVm){
        Species species = speciesMapper.toSpecies(speciesVm);
        Species createdSpecies = speciesServiceImp.save(species);
        SpeciesResponseVm userResponseVm = speciesMapper.toVM(createdSpecies);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Species created successfully");
        response.put("species", userResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<Map<String, Object>> updateSpecies(@PathVariable UUID id, @RequestBody @Valid SpeciesVm speciesVm) {
        Species species = speciesMapper.toSpecies(speciesVm);
        species.setId(id);
        Species updatedSpecies = speciesServiceImp.update(species);
        SpeciesResponseVm speciesResponseVm = speciesMapper.toVM(updatedSpecies);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Species updated successfully");
        response.put("species", speciesResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable UUID id) {
      speciesServiceImp.delete(id);
     return ResponseEntity.ok("Species deleted successfully");
    }
  @GetMapping("/findByCategory")
    public ResponseEntity<Page<SpeciesResponseVm>> getSpeciesByCategory(@RequestParam SpeciesType category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

      Page<Species> speciesPage = speciesServiceImp.getSpeciesByCategory(category, page, size);
      List<SpeciesResponseVm> speciesResponseVms = speciesPage.getContent().stream().map(speciesMapper::toVM).toList();
      Page<SpeciesResponseVm> speciesResponsePage = new PageImpl<>(speciesResponseVms, speciesPage.getPageable(), speciesPage.getTotalElements());
      return ResponseEntity.ok(speciesResponsePage);
    }
    @GetMapping("all")
    public ResponseEntity<Page<SpeciesResponseVm>> getAllSpecies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Page<Species> speciesPage = speciesServiceImp.getAllSpeciesPaginated(page, size);
        List<SpeciesResponseVm> speciesResponseVms = speciesPage.getContent().stream().map(speciesMapper::toVM).toList();
        Page<SpeciesResponseVm> speciesResponsePage = new PageImpl<>(speciesResponseVms, speciesPage.getPageable(), speciesPage.getTotalElements());
        return ResponseEntity.ok(speciesResponsePage);
    }
}
