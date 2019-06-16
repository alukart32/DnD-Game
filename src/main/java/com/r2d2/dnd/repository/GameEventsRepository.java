package com.r2d2.dnd.repository;

import com.r2d2.dnd.game.events.Event;
import org.springframework.data.repository.CrudRepository;

public interface GameEventsRepository extends CrudRepository<Event, Long> {
    Iterable<Event> findAllByGameSessionId(Long id);
}
