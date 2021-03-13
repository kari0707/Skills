package com.kari.skills.utils;

import com.kari.skills.Main;
import com.kari.skills.skill.Variables;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class JobConfig {
    private final YamlConfiguration config;
    private final File file;

    public JobConfig(Main plugin) {
        file = new File(plugin.getDataFolder(), "Jobs.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        for(String uuid : config.getKeys(false)) {
            Variables.getInstance().jobs.put(UUID.fromString(uuid), config.getString(uuid));
        }
    }

    public void saveToData(UUID uuid) {
        config.set(uuid.toString(), Variables.getInstance().jobs.get(uuid));
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        for (UUID key : Variables.getInstance().jobs.keySet()) {
            config.set(key.toString(), Variables.getInstance().jobs.get(key));
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
