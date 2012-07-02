package com.simplyian.superplots.event;

import org.bukkit.event.Event;

import com.simplyian.superplots.plot.Plot;

/**
 * Represents an event that occurs with a plot.
 */
public abstract class PlotEvent extends Event {
    private Plot plot;

    public PlotEvent(Plot plot) {
        this.plot = plot;
    }

    /**
     * @return the plot
     */
    public Plot getPlot() {
        return plot;
    }

    /**
     * @param plot
     *            the plot to set
     */
    public void setPlot(Plot plot) {
        this.plot = plot;
    }
}
