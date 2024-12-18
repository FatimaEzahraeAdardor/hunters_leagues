package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.web.vm.SpeciesResponseVm;
import org.youcode.hunters_leagues.web.vm.SpeciesVm;

@Mapper(componentModel = "spring")

public interface SpeciesMapper {
        Species toSpeciesResponse(SpeciesResponseVm speciesResponseVM);
        SpeciesResponseVm toVM(Species species);
        Species toSpecies(SpeciesVm speciesVM);
}
