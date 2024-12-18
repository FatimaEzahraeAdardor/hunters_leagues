package org.youcode.hunters_leagues.web.vm;

import lombok.Getter;
import lombok.Setter;
import org.youcode.hunters_leagues.domain.enums.Difficulty;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;

import java.util.UUID;
@Getter
@Setter
public class SpeciesResponseVm {
    private UUID id;
    private String name;
    private SpeciesType category;
    private Double minimumWeight;
    private Difficulty difficulty;
    private Integer points;
}
