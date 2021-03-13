package com.kari.skills.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkillCompleter implements TabCompleter {

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp() || args.length >= 2) return null;
        List<String> list = new ArrayList<>();
        list.add("활성화");
        list.add("비활성화");
        list.add("로드");
        list.add("버전");
        list.add("ver");
        return list;
    }
}
