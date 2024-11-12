package org.youcode.hunters_leagues.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuntDto {
    @NotNull
    @Positive
    private Double weight;

    @NotNull
    private UUID species_id;

    @NotNull
    private UUID participation_id;
}
