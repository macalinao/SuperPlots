package com.simplyian.superplots.event;

import org.bukkit.event.HandlerList;

import com.simplyian.superplots.plot.Plot;

/**
 * Called when a plot is created.
 */
public class PlotCreateEvent extends PlotEvent {
    private static HandlerList handlers = new HandlerList();

    public PlotCreateEvent(Plot plot) {
        super(plot);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
