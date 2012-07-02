package com.simplyian.superplots.plot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.simplyian.superplots.SuperPlots;

public class PlotManager {
    private final SuperPlots main;

    private List<Plot> plots = new ArrayList<Plot>();

    public PlotManager(SuperPlots main) {
        this.main = main;
    }

    /**
     * Gets the closest plot at the given location. Uses edge distance, so it's
     * EXPENSIVE!
     * 
     * @param location
     * @return
     */
    public Plot getClosestPlotAt(Location location) {
        double closestDist = 1000000000000.0;
        Plot closest = null;
        for (Plot plot : plots) {
            double dist = plot.edgeDistance(location);
            if (dist < closestDist) {
                closestDist = dist;
                closest = plot;
            }
        }
        return closest;
    }

    /**
     * Gets the plot at the given location.
     * 
     * @param location
     * @return
     */
    public Plot getPlotAt(Location location) {
        for (Plot plot : plots) {
            if (plot.contains(location)) {
                return plot;
            }
        }
        return null;
    }

    /**
     * Creates a plot.
     * 
     * @param name
     * @param owner
     * @param size
     * @param center
     * @return
     */
    public Plot createPlot(String name, String owner, int size, Location center) {
        Plot plot = new Plot(name, owner, size, center);
        plots.add(plot);
        return plot;
    }

}
