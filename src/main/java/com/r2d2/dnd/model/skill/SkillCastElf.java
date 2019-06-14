package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.dto.SkillCastDTO;

public class SkillCastElf implements SkillCastBehavior {
    public SkillCastDTO cast(Player player, Player other) {
        SkillCastDTO response = new SkillCastDTO();

        player.setLvl(player.getLvl()+3);

        response.setPlayer(player);
        response.setOther(null);

        response.setSideEffect(SkillSideEffect.SUPER_LVL_DOWN);
        return response;
    }
}
