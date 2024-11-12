package org.youcode.hunters_leagues.service;

import org.youcode.hunters_leagues.domain.Hunt;
import org.youcode.hunters_leagues.service.dto.HuntDto;

public interface HuntService {
    Hunt save(HuntDto huntDto);
}
