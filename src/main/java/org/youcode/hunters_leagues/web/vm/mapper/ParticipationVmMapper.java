package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.Participation;
import org.youcode.hunters_leagues.web.vm.HistoricalParticipationVm;
import org.youcode.hunters_leagues.web.vm.ParticipationResponseVm;
import org.youcode.hunters_leagues.web.vm.ParticipationVm;

@Mapper(componentModel = "spring")
public interface ParticipationVmMapper {
    Participation ToEntity(ParticipationVm participationVm);
    ParticipationResponseVm ToVM(Participation participation);
    HistoricalParticipationVm ToHISTORICAL_PARTICIPATION_Vm(Participation participation);
}
