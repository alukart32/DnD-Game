package com.r2d2.dnd.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    private String race;
    private int lvl;
    private char doneAction;
    private int stamina;
}
