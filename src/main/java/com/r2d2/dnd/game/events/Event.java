package com.r2d2.dnd.game.events;

import com.r2d2.dnd.game.dto.CharacterDTO;
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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne
    GameSession gameSession;

    @Length(max = 1)
    private char player;
}
