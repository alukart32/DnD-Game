package com.r2d2.dnd.model;

import com.r2d2.dnd.model.skill.SkillCastBehavior;
import lombok.Getter;
import lombok.Setter;

/**
 * Character - персонаж, выбираемый игроком.
 * Каждый из них обладает индивиндуальными различиями в
 *  1) max выносливости
 *  2) трате выносливости при "бычтром спуске"
 *  3) трате выносливости при использовании уникального умения
 */
@Getter
@Setter
public abstract class Character {
    // раса персонажа
    private String race;
    // max выносливость ( 30 <= s <= 50)
    private int maxStamina;
    // трата выносливости на "быстрый спуск"
    private int fastTravelStamina;
    // трата выносливости на использовании уникального скила
    private int skillStamina;

    SkillCastBehavior skillCastBehavior;

    public Character() {}

    public Character(int maxStamina, int fastTravelStamina,
                     int skillStamina, SkillCastBehavior skillCastBehavior) {
        this.maxStamina = maxStamina;
        this.fastTravelStamina = fastTravelStamina;
        this.skillStamina = skillStamina;
        this.skillCastBehavior = skillCastBehavior;
    }
}
