package com.r2d2.dnd.game;

import com.r2d2.dnd.game.dto.SkillCastDTO;
import com.r2d2.dnd.model.Character;
import com.r2d2.dnd.model.skill.SkillSideEffect;
import org.springframework.stereotype.Component;

/**
 * Представляет собой "состояние" игрока во время игры.
 * Хранить character(тип персонажа), lvl, action (что сделал на ходе), currentStamina (текущая выносливость)
 */
@Component
public class Player {
    // выбранный персонаж
    private Character character;
    // текущий уровень
    private int lvl = 1;
    // текущая выносливость
    private int currentStamina;

    // текущий эффект skill у персонажа
    private SkillSideEffect skillBuff = SkillSideEffect.WITHOUT;
    // оказывающий эффект skill другого персонажа
    private SkillSideEffect otherSkillBuff = SkillSideEffect.WITHOUT;

    /**
     * Отдых - отнимает 0 выносливости.
     * Эффект - восстанавливает 3 ед./энергии
     */
    public void rest(){ currentStamina +=3;}

    /**
     * Спуск - отнимает 5 выносливости.
     * Эффект - на этаж вниз
     */
    public void down(){
        currentStamina -=5;
        lvl++;
    }

    /**
     * Быстрый спуск - отнимает X выносливости (зависит от персонажа)
     * Эффект - на 2 этажа вниз
     */
    public void fastTravel(){
        currentStamina -=this.character.getFastTravelStamina();
        lvl+=2;
    }

    /**
     * Особое действие за Y выносливости
     */
    public SkillCastDTO skill(Player other){

        currentStamina -= this.character.getSkillStamina();

        SkillCastDTO skillCastDTO = character.getSkillCastBehavior().cast(this, other);
        lvl = skillCastDTO.getPlayer().getLvl();
        skillBuff = skillCastDTO.getSideEffect();

        return skillCastDTO;
    }

    public SkillSideEffect getSkillBuff() { return skillBuff; }

    public SkillSideEffect getOtherSkillBuff() { return otherSkillBuff; }

    public int getMaxStamina(){ return character.getMaxStamina();}

    public int getFastDownStamina(){return character.getFastTravelStamina();}

    public int getSkillStamina(){return character.getSkillStamina();}

    public void setOtherSkillBuff(SkillSideEffect otherSkillBuff) { this.otherSkillBuff = otherSkillBuff; }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getCurrentStamina() {
        return currentStamina;
    }

    public void setCurrentStamina(int currStamina) {
        this.currentStamina = currStamina;
    }

    public String getRace(){ return  character.getRace(); }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
