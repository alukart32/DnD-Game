package com.r2d2.dnd.game.session;

import com.r2d2.dnd.game.events.Event;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SequenceGenerator(name="games", initialValue=1, allocationSize=1)
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games")
    Long id;

    @Length(max = 10)
    String playerOne;

    @Length(max = 10)
    String playerTwo;

    String winner;

    @OneToMany(mappedBy = "gameSession", fetch = FetchType.EAGER)
    Set<Event> events = new HashSet<>();

    public GameSession addEvent(Event event){
        event.setGameSession(this);
        events.add(event);
        return this;
    }
}
