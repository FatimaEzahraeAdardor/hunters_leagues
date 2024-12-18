package org.youcode.hunters_leagues.web.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.youcode.hunters_leagues.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class HuntResponseVm {

   private UUID id;
    private ParticipationResponseVm participation;
    private SpeciesResponseVm species;
    private Double weight;
}
