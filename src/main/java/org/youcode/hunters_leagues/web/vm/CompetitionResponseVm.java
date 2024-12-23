package org.youcode.hunters_leagues.web.vm;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CompetitionResponseVm {
        private UUID id;
        private String location;
        private LocalDateTime date;
        private Integer numberOfParticipants;
}
