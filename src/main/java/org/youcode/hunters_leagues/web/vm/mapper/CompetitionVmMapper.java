package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.web.vm.CompetitionResponseVm;
import org.youcode.hunters_leagues.web.vm.CompetitionVm;

@Mapper(componentModel = "spring")
public interface CompetitionVmMapper {
    Competition toEntity(CompetitionVm competitionVm);
    CompetitionResponseVm toVM(Competition competition);

}
