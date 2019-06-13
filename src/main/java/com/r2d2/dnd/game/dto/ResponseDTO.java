package com.r2d2.dnd.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Результат хода игрока
 */
@Getter
@Setter
public class ResponseDTO {
    private Optional<CharacterDTO> player;
    private Optional<CharacterDTO> playerTwo;
}
