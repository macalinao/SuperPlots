package com.simplyian.superplots.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.simplyian.superplots.plot.Plot;

public final class PlotBuildEvent extends PlotEvent implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    /**
     * True if place, false if break.
     */
    private final boolean place;

    private final Player player;

    private final Block block;

    private boolean cancelled;

    public PlotBuildEvent(Plot plot, boolean place, Player player, Block block) {
        super(plot);
        this.place = place;
        this.player = player;
        this.block = block;
    }

    /**
     * Gets the block broken or placed in this event.
     * @return the block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * @return the place
     */
    public boolean isPlace() {
        return place;
    }

    /**
     * @return the place
     */
    public boolean isBreak() {
        return !place;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
