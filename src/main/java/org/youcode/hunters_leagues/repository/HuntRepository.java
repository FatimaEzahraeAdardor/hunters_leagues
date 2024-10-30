package org.youcode.hunters_leagues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.hunters_leagues.domain.Hunt;

import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt, UUID> {
}
