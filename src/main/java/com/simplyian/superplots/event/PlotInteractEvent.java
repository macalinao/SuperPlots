package com.simplyian.superplots.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

import com.simplyian.superplots.plot.Plot;

/**
 * Called when a plot is interacted with.
 */
public class PlotInteractEvent extends PlotEvent implements Cancellable {
    private static HandlerList handlers = new HandlerList();
    private Player player;
    private Block block;
    private Action action;
    private boolean cancelled;

    public PlotInteractEvent(Player player, Block block, Action action,
            Plot plot) {
        super(plot);
        this.player = player;
        this.block = block;
        this.action = action;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * @return the action
     */
    public Action getAction() {
        return action;
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
