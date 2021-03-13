package com.kari.skills.commands;

import com.kari.skills.Main;
import com.kari.skills.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JobCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("job")) {
            if(args.length <= 0) {
                //직업 표기
                player.sendMessage(Main.FORMAT + "당신의 직업은 " + Util.getJob(player) + "§f입니다.");
                if(player.isOp()) {
                    player.sendMessage(Main.FORMAT + "/직업 변경 <플레이어> <직업이름> - 플레이어의 직업을 변경합니다.");
                }
                return false;
            }
            if(args[0].equalsIgnoreCase("변경")) {
                if(!player.isOp()) return false;
                if(args.length <= 1) {
                    player.sendMessage(Main.FORMAT + "플레이어의 이름을 입력해주세요.");
                    return false;
                }
                else if(args.length <= 2) {
                    player.sendMessage(Main.FORMAT + args[0] + "할 직업이름을 입력해주세요.");
                    return false;
                }
                Player p = Util.getPlayer(args[1]);
                String job = args[2];
                Util.setJob(p, job);
                player.sendMessage(Main.FORMAT + p.getName() + "님의 직업을 " + Util.getJob(player) + "§f으(로) " + args[0] + "하였습니다.");
            }
        }
        return false;
    }
}
