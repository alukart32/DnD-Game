package com.r2d2.dnd.model;

import com.r2d2.dnd.model.skill.SkillCastBehavior;

public class Mage extends Character {
    public Mage(int maxStamina, int fastTravelStamina,
                int skillStamina, SkillCastBehavior skillCastBehavior) {
        super(maxStamina, fastTravelStamina, skillStamina, skillCastBehavior);
        this.race = "mage";
    }
}
