package org.youcode.hunters_leagues.service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResultDto {
    private UUID competitionId;
    private String competitionCode;
    private String location;
    private LocalDateTime date;
    private Double score;
}
