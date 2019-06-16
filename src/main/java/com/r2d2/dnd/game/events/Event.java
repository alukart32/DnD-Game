package com.r2d2.dnd.game.events;

import com.r2d2.dnd.game.session.GameSession;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Представление событиев (ходов) во время игры
 */
@Data
@EqualsAndHashCode(exclude = "gameSession")
@Entity
@SequenceGenerator(name="events", initialValue=1, allocationSize=1)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events")
    Long id;

    @ManyToOne
    GameSession gameSession;

    @Length(max = 20)
    private String player;

    private int move;

    private int lvl;

    private int stamina;

    private String action;
}
