package com.kari.skills;

import com.kari.skills.commands.SkillCompleter;
import com.kari.skills.events.Event;
import com.kari.skills.listeners.AccessListener;
import com.kari.skills.skill.SkillManager;
import com.kari.skills.commands.JobCommand;
import com.kari.skills.commands.SkillCommand;
import com.kari.skills.listeners.SkillListener;
import com.kari.skills.utils.JobConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private static SkillManager manager;
    public static String FORMAT;
    private static JobConfig config;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("스킬").setExecutor(new SkillCommand());
        getCommand("스킬").setTabCompleter(new SkillCompleter());
        getCommand("job").setExecutor(new JobCommand());
        reload();
    }

    public void onDisable() {
        config.saveAll();
    }

    public void reload() {
        reloadConfig();
        FileConfiguration config = getConfig();
        FORMAT = config.getString("Format");
        instance = this;
        manager = new SkillManager();
        Main.config = new JobConfig(this);
        if(config.getBoolean("Event-Auto-Enable")) registerEvents();
    }

    public static JobConfig getJopConfig() {
        return config;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new SkillListener(), this);
        getServer().getPluginManager().registerEvents(new AccessListener(), this);
    }

    public void unregisterEvents() {
        PlayerItemHeldEvent.getHandlerList().unregister(this);
        InventoryClickEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        Event.unregister(this);
    }

    public static SkillManager getSkillManager() {
        return manager;
    }

    public static Main getInstance() {
        return instance;
    }
}
