package com.simplyian.superplots.data.persistence;

import java.util.List;

import com.simplyian.superplots.plot.Plot;

/**
 * Persists data.
 * 
 * @author simplyianm
 */
public interface DataPersistor {
    /**
     * Saves the given plot and writes it to the storage mechanism.
     * 
     * @param plot
     */
    public void savePlot(Plot plot);

    /**
     * Loads all stored plots into memory.
     * 
     * @return The stored plots.
     */
    public List<Plot> loadPlots();

    /**
     * Writes all of the given plots to the storage mechanism.
     * 
     * @param plots
     */
    public void savePlots(List<Plot> plots);
}
