package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.dto.SkillCastDTO;

import java.util.Optional;

public class SkillCastMage implements SkillCastBehavior {
    @Override
    public SkillCastDTO cast(Player player, Player other) {
        SkillCastDTO response = new SkillCastDTO();
        // если второй игрок на следующем уровне
        if(other.getLvl()+1 > player.getLvl()){
            int tmpLvl = other.getLvl();
            other.setLvl(player.getLvl());
            player.setLvl(tmpLvl);

            // сохранили изменение состояние игроков
            response.setPlayer(player);
            response.setOther(other);
        }else {
            player.setLvl(player.getLvl()+1);
            response.setPlayer(player);
            response.setOther(other);
        }

        response.setSideEffect(SkillSideEffect.CHANGE_POS);
        return response;

    }
}
