package com.r2d2.dnd.game;

import com.r2d2.dnd.game.dto.CharacterDTO;
import com.r2d2.dnd.game.dto.ResponseDTO;
import com.r2d2.dnd.model.Character;
import org.springframework.stereotype.Component;

/**
 * Представляет собой "состояние" игрока во время игры.
 * Хранить character(тип персонажа), lvl, action (что сделал на ходе), currStamina (текущая выносливость)
 */
@Component
public class Player {
    // выбранный персонаж
    private Character character;
    // текущий уровень
    private int lvl = 1;
    // текущая выносливость
    private int currStamina;

    /**
     * Отдых - отнимает 0 выносливости.
     * Эффект - восстанавливает 3 ед./энергии
     */
    public void rest(){ currStamina+=3;}

    /**
     * Спуск - отнимает 5 выносливости.
     * Эффект - на этаж вниз
     */
    public void down(){
        currStamina-=5;
        lvl++;
    }

    /**
     * Быстрый спуск - отнимает X выносливости (зависит от персонажа)
     * Эффект - на 2 этажа вниз
     */
    public void fastTravel(){
        currStamina-=this.character.getFastTravelStamina();
        lvl+=2;
    }

    /**
     * Особое действие за Y выносливости
     */
    public ResponseDTO skill(CharacterDTO playerTwo){
        character.maxStamina-=this.character.skillStamina;

        CharacterDTO player = new CharacterDTO();
        player.setLvl(lvl);
        player.setRace(character.getRace());

        return character.getSkillCastBehavior().cast(player, playerTwo);
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getCurrStamina() {
        return currStamina;
    }

    public void setCurrStamina(int currStamina) {
        this.currStamina = currStamina;
    }

    public String getRace(){
        return  character.getRace();
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
