package com.simplyian.superplots.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.simplyian.superplots.plot.Plot;

/**
 * Called when a player exits a plot.
 * 
 * @author simplyianm
 */
public class PlotExitEvent extends PlotEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private Player player;

    public PlotExitEvent(Player player, Plot plot) {
        super(plot);
        this.player = player;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

}
