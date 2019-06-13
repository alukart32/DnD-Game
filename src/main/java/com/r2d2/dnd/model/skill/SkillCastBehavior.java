package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.dto.CharacterDTO;
import com.r2d2.dnd.game.dto.ResponseDTO;

public interface SkillCastBehavior {
    public ResponseDTO cast(CharacterDTO player, CharacterDTO playerTwo);
}
