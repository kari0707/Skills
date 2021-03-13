package com.kari.skills.skill;

import com.kari.skills.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class SkillManager {

    private static HashMap<String, Inventory> skills;
    private static final ItemStack line = Skill.getKindItem(Kind.LINE);

    public SkillManager() {
        skills = new HashMap<>();
        load();
    }

    public void load() {
        update(new Skills(Main.getInstance()));
    }

    public void update(Skills skills) {
        SkillManager.skills.clear();
        HashMap<String, List<Skill>> var1 = skills.getSkills();
        for(String job : var1.keySet()) {
            Inventory var2 = Bukkit.createInventory(null, 54, job + " §c§l스킬 목록");
            List<Skill> var3 = var1.get(job);
            for (int i = 36; i < 45; i++) {
                var2.setItem(i, Skill.getKindItem(Kind.LINE));
            }
            for (int a = 10; a < var3.size() + 10; a++) {
                if ((a + 1) % 9 == 0) {
                    var2.setItem(a + 2, var3.get(a - 10).getItem());
                    continue;
                }
                var2.setItem(a, var3.get(a - 10).getItem());
                var2.setItem(53, Skill.getKindItem(Kind.INIT));
            }
            SkillManager.skills.put(job, var2);
        }
    }

    public boolean containsJob(String job) {
        return skills.containsKey(job);
    }

    public Inventory getSkillGUI(String job, Player player) {
        Inventory inv = skills.get(job);
        ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if(item == null) continue;
            else if(item.isSimilar(line)) break;
            inv.setItem(i, Skill.levelLimit(item, player.getLevel()));
        }
        return skills.get(job);
    }
}
