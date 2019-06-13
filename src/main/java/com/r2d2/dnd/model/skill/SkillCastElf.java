package com.r2d2.dnd.model.skill;

import com.r2d2.dnd.game.dto.CharacterDTO;
import com.r2d2.dnd.game.dto.ResponseDTO;

import java.util.Optional;

public class SkillCastElf implements SkillCastBehavior {
    public ResponseDTO cast(CharacterDTO player, CharacterDTO playerTwo) {
        ResponseDTO response = new ResponseDTO();
        // если второй игрок на следующем уровне
        if(playerTwo.getLvl()+1 == player.getLvl()){
            int tmpLvl = playerTwo.getLvl();
            playerTwo.setLvl(player.getLvl());
            player.setLvl(tmpLvl);

            response.setPlayerTwo(Optional.of(playerTwo));

        }else {
            player.setLvl(player.getLvl()+1);
        }
        response.setPlayer(Optional.of(player));

        return response;
    }
}
