package com.kari.skills.events;

import com.kari.skills.skill.Skill;
import org.bukkit.entity.Player;

public class SkillChangeEvent extends Event {

    private Skill skill;

    public SkillChangeEvent(Player player) {
        super(player);
    }

    public Skill getSkill() {
        return skill;
    }
}
