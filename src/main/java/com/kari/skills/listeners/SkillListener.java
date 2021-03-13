package com.kari.skills.listeners;

import com.kari.skills.skill.Kind;
import com.kari.skills.skill.Skill;
import com.kari.skills.skill.Variables;
import com.nisovin.magicspells.Spell;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SkillListener implements Listener {

    private static final ItemStack slot = Skill.getKindItem(Kind.SLOT);
    private static final ItemStack line = Skill.getKindItem(Kind.LINE);
    private static final ItemStack init = Skill.getKindItem(Kind.INIT);

    @EventHandler
    public void onSkillOpen(InventoryOpenEvent event) {
        String name = event.getView().getTitle();
        if(!name.contains("스킬")) return;
        Inventory inv = event.getInventory();
        updateSkillGUI(inv, (Player) event.getPlayer());
    }

    @EventHandler
    public void onSkillHeld(PlayerItemHeldEvent event) {
        int slot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(slot);
        if(previousSlot >= 1 && previousSlot <= 6) event.setCancelled(true);
        if (!(slot >= 1 && slot <= 6)) return;
        event.setCancelled(true);
        if (stack == null) return;
        if (stack.isSimilar(SkillListener.slot) || stack.isSimilar(line)) return;
        HashMap<String, Spell> spellKey = Variables.getInstance().spellKey;
        String section = stack.getItemMeta().getLocalizedName();
        if (!spellKey.containsKey(section)) return;
        Spell spell = spellKey.get(section);
        spell.cast(player);
        if(player.hasCooldown(stack.getType())) return;
        player.setCooldown(stack.getType(), (int) spell.getCooldown() * 20);
    }

    @EventHandler
    public void onSkillClick(InventoryClickEvent event) {
        ItemStack current = event.getCurrentItem();
        String name = event.getView().getTitle();
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        if(inv == player.getInventory()) {
            if(event.getSlot() >= 1 && event.getSlot() <= 6) {
                event.setCancelled(true);
                return;
            }
        }
        if(clickType == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }
        if(!name.contains("스킬")) return;
        event.setCancelled(true);
        if(current == null || inv == null || current.isSimilar(slot) || current.isSimilar(line)) return;
        if(event.getSlot() == 53 && current.isSimilar(init)) {
            for (int i = 1; i <= 6; i++) {
                player.getInventory().setItem(i, slot);
                updateSkillGUI(inv, player);
            }
            return;
        }
        if(event.getSlot() >= 46 && event.getSlot() <= 51) {
            player.getInventory().setItem(event.getSlot() - 45, slot);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 2, 1);
            updateSkillGUI(inv, player);
            return;
        }
        if(clickType.isLeftClick()) {
            for (int i = 1; i <= 6; i++) {
                if (!player.getInventory().contains(current)) {
                    if (Integer.parseInt(current.getItemMeta().getLore().get(1).replaceAll("[^0-9]", "")) <= player.getLevel()) {
                        if (player.getInventory().getItem(i).isSimilar(slot)) {
                            player.getInventory().setItem(i, current);
                            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 2, 1);
                        } else continue;
                    } else {
                        player.sendMessage("§3§l[ §b§l스킬 §3§l]§f 레벨 조건을 충족하지 못했습니다.");
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        return;
                    }
                } else {
                    player.sendMessage("§3§l[ §b§l스킬 §3§l]§f 해당 스킬은 이미 장착 상태입니다.");
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    return;
                }
                updateSkillGUI(inv, player);
                break;
            }
            updateSkillGUI(inv, player);
        } else if(clickType.isRightClick()) {
            ItemStack stack;
            for (int i = 1; i <= 6; i++) {
                stack = player.getInventory().getItem(i);
                if(current.isSimilar(stack)) {
                    player.getInventory().setItem(i, slot);
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 2, 1);
                }
            }
            updateSkillGUI(inv, player);
        }
    }

    private static void updateSkillGUI(Inventory inv, Player player) {
        for (int i = 46; i <= 51; i++) {
            inv.setItem(i, player.getInventory().getItem(i - 45));
        }
        player.updateInventory();
    }
}
