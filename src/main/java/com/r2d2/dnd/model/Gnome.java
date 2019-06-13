package com.r2d2.dnd.model;


import com.r2d2.dnd.model.skill.SkillCastBehavior;

public class Gnome extends Character {
    public Gnome(int maxStamina, int fastTravelStamina,
                 int skillStamina, SkillCastBehavior skillCastBehavior) {
        super(maxStamina, fastTravelStamina, skillStamina, skillCastBehavior);
        this.race = "gnome";
    }
}
