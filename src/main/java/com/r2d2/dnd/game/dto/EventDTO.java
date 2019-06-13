package com.r2d2.dnd.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Хранить свойства события: что за действие,
 * при использовании "особого действия" хранить и
 * информацию и о другом игроке (тип персонажа и уровень)
 */
@Getter
@Setter
public class EventDTO {
    private char action;
    private Optional<CharacterDTO> playerTwo;
}
