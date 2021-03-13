package com.kari.skills.commands;

import com.kari.skills.Main;
import com.kari.skills.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("스킬")) {
                String job = ChatColor.stripColor(Util.getJob(player));
                if(args.length <= 0) {
                    if(!Main.getSkillManager().containsJob(job)) {
                        player.sendMessage(Main.FORMAT + "존재하지 않거나 없는 직업입니다.");
                        return false;
                    }
                    player.openInventory(Main.getSkillManager().getSkillGUI(job, player));
                }
                else if(args[0].equalsIgnoreCase("로드")) {
                    Main.getInstance().reload();
                    player.sendMessage(Main.FORMAT + "로드되었습니다.");
                }
                else if(args[0].equalsIgnoreCase("활성화")) {
                    Main.getInstance().registerEvents();
                    player.sendMessage(Main.FORMAT + "스킬 시스템이 활성화 되었습니다.");
                }
                else if(args[0].equalsIgnoreCase("비활성화")) {
                    Main.getInstance().unregisterEvents();
                    player.sendMessage(Main.FORMAT + "스킬 시스템이 비활성화 되었습니다.");
                }
                else if(args[0].equalsIgnoreCase("버전") || args[0].contains("ver")) {
                    player.sendMessage(Main.FORMAT + "Ver: §a" + Main.getInstance().getDescription().getVersion());
                }
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage(Main.FORMAT + "에러 발생.");
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
