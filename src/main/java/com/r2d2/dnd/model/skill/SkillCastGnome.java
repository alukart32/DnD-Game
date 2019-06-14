package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.dto.SkillCastDTO;

public class SkillCastGnome implements SkillCastBehavior {
    @Override
    public SkillCastDTO cast(Player player, Player other) {
        SkillCastDTO response = new SkillCastDTO();

        player.setLvl(player.getLvl()+1);

        response.setPlayer(player);
        response.setOther(null);

        response.setSideEffect(SkillSideEffect.YOU_SHALL_NOT_PASS);
        return response;
    }
}
