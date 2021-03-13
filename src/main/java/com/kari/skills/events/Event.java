package com.kari.skills.events;

import com.kari.skills.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class Event extends PlayerEvent implements Cancellable {

    private boolean cancel;
    private static final HandlerList handlerList = new HandlerList();

    protected Event(Player player) {
        super(player);
        cancel = false;
    }

    public final boolean isCancelled() {
        return cancel;
    }

    public final void setCancelled(boolean b) {
        this.cancel = b;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @NotNull
    public final HandlerList getHandlers() {
        return handlerList;
    }

    public static void unregister(Main plugin) {
        getHandlerList().unregister(plugin);
    }
}
