package com.r2d2.dnd.repository;

import com.r2d2.dnd.game.session.GameSession;
import org.springframework.data.repository.CrudRepository;

public interface GameSessionRepository extends CrudRepository<GameSession, Long> {
}
