package com.kari.skills.skill;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum SkillType {

    NORMALATTACK, ACTIVE, BUFFC, BUFFP;

    private final static List<String> types = new ArrayList<>();
    private final static Map<String, SkillType> BY_NAME = Maps.newHashMap();

    static {
        for (SkillType type : values()) {
            BY_NAME.put(type.name(), type);
        }
        types.addAll(Arrays.asList("NORMALATTACK", "ACTIVE", "BUFFC", "BUFFP"));
    }

    public static boolean isBuff(SkillType type) {
        return type == SkillType.BUFFC || type == SkillType.BUFFP;
    }

    public static ChatColor color(SkillType type) {
        ChatColor color;
        switch (type) {
            case BUFFC:
                color = ChatColor.GREEN;
                break;
            case ACTIVE:
                color = ChatColor.RED;
                break;
            case BUFFP:
                color = ChatColor.LIGHT_PURPLE;
                break;
            case NORMALATTACK:
                color = ChatColor.GOLD;
                break;
            default:
                color = ChatColor.WHITE;
                break;
        }
        return color;
    }

    public static SkillType toType(String obj) {
        SkillType type;
        String filtered = obj.toUpperCase(java.util.Locale.ENGLISH);
        if(!types.contains(filtered)) return null;
        filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
        type = BY_NAME.get(filtered);
        return type;
    }

    public static String toKRStringType(SkillType type) {
        String name;
        switch (type) {
            case BUFFC:
                name = "개인버프";
                break;
            case ACTIVE:
                name = "액티브";
                break;
            case BUFFP:
                name = "파티버프";
                break;
            case NORMALATTACK:
                name = "기본공격";
                break;
            default:
                name = "";
                break;
        }
        return name;
    }
}
