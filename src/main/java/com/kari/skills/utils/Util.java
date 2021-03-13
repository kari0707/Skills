package com.kari.skills.utils;

import com.kari.skills.skill.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {

    public static Player getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player == null) player = (Player) Bukkit.getOfflinePlayer(name);
        return player;
    }

    public static String uncolored(String input) {
        return ChatColor.stripColor(input);
    }

    public static String getJob(Player player) {
        return Variables.getInstance().jobs.get(player.getUniqueId()).replaceAll("&", "§");
    }

    public static void setJob(Player player, String job) {
        Variables.getInstance().jobs.put(player.getUniqueId(), job);
    }
}
