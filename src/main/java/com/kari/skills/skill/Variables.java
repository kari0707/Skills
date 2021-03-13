package com.kari.skills.skill;

import com.nisovin.magicspells.Spell;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.UUID;

public class Variables {

    public final HashMap<String, ConfigurationSection> UPDATEDATA;
    private static Variables instance;
    public HashMap<String, Spell> spellKey;
    public HashMap<UUID, String> jobs;

    public static Variables getInstance() {
        if(instance == null) instance = new Variables();
        return instance;
    }

    public Variables() {
        instance = this;
        UPDATEDATA = new HashMap<>();
        spellKey = new HashMap<>();
        jobs = new HashMap<>();
    }
}
