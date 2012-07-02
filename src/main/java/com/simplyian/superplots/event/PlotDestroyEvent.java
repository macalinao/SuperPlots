package com.simplyian.superplots.event;

import org.bukkit.event.HandlerList;

import com.simplyian.superplots.plot.Plot;

public class PlotDestroyEvent extends PlotEvent {
    private static HandlerList handlers = new HandlerList();

    public PlotDestroyEvent(Plot plot) {
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
