package com.kari.skills.skill;

import com.kari.skills.Main;
import com.kari.skills.utils.Util;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.spells.BuffSpell;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Skill {

    private String sectionName;
    private String display;
    private Material icon;
    private SkillType type;
    private int level;
    private Spell spell;
    private List<String> lore;
    private List<String> buffLore;

    public Skill(ConfigurationSection section) {
        try {
            this.sectionName = section.getName();
            this.display = section.getString("Display", "&fNONE");
            this.icon = Material.valueOf(section.getString("Icon", "DIAMOND").toUpperCase());
            this.level = section.getInt("Level", 0);
            this.spell = MagicSpells.getSpellByInternalName(section.getString("MagicSpellCast"));
            if(spell == null) spell = MagicSpells.getSpellByInGameName(section.getString("Spell", "NONE"));
            this.lore = section.getStringList("Lore");
            this.type = SkillType.toType(section.getString("Type", "NormalAttack"));
            if (type == SkillType.BUFFC || type == SkillType.BUFFP) buffLore = section.getStringList("BuffEffect-Lore");
            Variables.getInstance().UPDATEDATA.put(this.sectionName, section);
            Variables.getInstance().spellKey.put(section.getName(), this.spell);
        } catch (Exception ignored) {
            if(spell == null && level != 0) System.out.println("존재하지 않는 스펠");
        }
    }

    public static ItemStack update(ItemStack stack) {
        if(stack == null || stack.isSimilar(getKindItem(Kind.SLOT))) return getKindItem(Kind.SLOT);
        String name = stack.getItemMeta().getLocalizedName();
        Variables variables = Variables.getInstance();
        if(!variables.UPDATEDATA.containsKey(name)) return getKindItem(Kind.SLOT);
        Skill skill = new Skill(variables.UPDATEDATA.get(name));
        return skill.getItem();
    }

    public int getLevel() {
        return level;
    }

    public List<String> getBuffLore() {
        return buffLore;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getIcon() {
        return icon;
    }

    public SkillType getType() {
        return type;
    }

    public Spell getSpell() {
        return spell;
    }

    public String getDisplay() {
        return display;
    }

    public ItemStack getItem() {
        ItemStack stack = new ItemStack(this.icon, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(this.display.replaceAll("&", "§"));
        meta.setLocalizedName(sectionName);
        List<String> list = new ArrayList<>(Arrays.asList("", "§f- 레벨 제한: §a" + this.level,
                "§f- 타입: " + SkillType.color(type) + SkillType.toKRStringType(type), "§f- 재사용 대기시간: §7" + spell.getCooldown() + "초", ""));

        if(SkillType.isBuff(type)) {
            list.add("§f- 버프 지속시간: §7" + ((BuffSpell) spell).getDuration() + "초");
            if(!this.buffLore.isEmpty()) list.add("§f[버프 효과]");
            list.addAll(this.buffLore);
        }
        if(!lore.isEmpty()) {
            list.add("§f[스킬 설명]");
            list.addAll(lore);
        } else list.remove(list.size() - 1);

        for(int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replaceAll("&", "§"));
        }

        meta.setLore(list);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack levelLimit(ItemStack stack, int level) {
        stack = stack.clone();
        ItemMeta meta = stack.getItemMeta();
        if(meta == null) return stack;
        List<String> lore = meta.getLore();
        if(lore == null || lore.size() < 4) return stack;
        int limit = Integer.parseInt(Util.uncolored(lore.get(1).replaceAll("[^0-9]", "")));
        if(limit > level) lore.set(1, "§f- 레벨 제한: §c" + limit);
        else lore.set(1, "§f- 레벨 제한: §a" + limit);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getKindItem(Kind k) {
        ItemStack slot;
        FileConfiguration config = Main.getInstance().getConfig();
        String str = config.getString("SKILL-" + k.name() + ".Icon");
        slot = new ItemStack(Material.valueOf(str.toUpperCase()));
        ItemMeta meta = slot.getItemMeta();
        meta.setDisplayName("§f" + config.getString("SKILL-" + k.name() + ".Name"));
        List<String> list = config.getStringList("SKILL-" + k.name() + ".Lore");
        while(list.contains("")) {
            list.remove("");
        }
        meta.setLore(list);
        slot.setItemMeta(meta);
        return slot;
    }
}
