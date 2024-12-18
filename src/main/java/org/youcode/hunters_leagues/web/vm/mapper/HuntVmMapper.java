package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.Competition;
import org.youcode.hunters_leagues.domain.Hunt;
import org.youcode.hunters_leagues.service.dto.HuntDto;
import org.youcode.hunters_leagues.web.vm.CompetitionResponseVm;
import org.youcode.hunters_leagues.web.vm.CompetitionVm;
import org.youcode.hunters_leagues.web.vm.HuntResponseVm;

@Mapper(componentModel = "spring")
public interface HuntVmMapper {
    Hunt toEntity(HuntDto huntDto);
    HuntResponseVm toVM(Hunt hunt );

}
