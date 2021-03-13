package com.kari.skills.listeners;

import com.kari.skills.Main;
import com.kari.skills.skill.Skill;
import com.kari.skills.skill.Variables;
import com.kari.skills.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class AccessListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!Variables.getInstance().jobs.containsKey(player.getUniqueId())) Util.setJob(player, "초심자");
        for (int i = 1; i <= 6; i++) {
            ItemStack update = Skill.update(player.getInventory().getItem(i));
            player.getInventory().setItem(i, update);
        }
        player.getInventory().setHeldItemSlot(0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Main.getJopConfig().saveToData(event.getPlayer().getUniqueId());
        Variables.getInstance().jobs.remove(event.getPlayer().getUniqueId());
    }
}