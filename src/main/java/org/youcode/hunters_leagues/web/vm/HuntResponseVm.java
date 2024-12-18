package org.youcode.hunters_leagues.web.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HuntResponseVm {

   private UUID id;
    private ParticipationResponseVm participation;
    private SpeciesResponseVm species;
    private Double weight;
}
