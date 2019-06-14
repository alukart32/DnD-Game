package com.r2d2.dnd.game.dto;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.model.skill.SkillSideEffect;
import lombok.Getter;
import lombok.Setter;

/**
 * Какой эффект от применения skill получаем
 */
@Getter
@Setter
public class SkillCastDTO {
    SkillSideEffect sideEffect;

    Player player;
    Player other;
}
