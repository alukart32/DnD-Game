package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.dto.SkillCastDTO;

/**
 * Определение сути skill персонажа
 */
public interface SkillCastBehavior {
    SkillCastDTO cast(Player player, Player other);
}
