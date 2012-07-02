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

    public Plot getPlotAt(Location location) {
        for (Plot plot : plots) {
            if (plot.contains(location)) {
                return plot;
            }
        }
        return null;
    }

    public Plot createPlot(String name, String owner, int size, Location center) {
        Plot plot = new Plot(name, owner, size, center);
        plots.add(plot);
        return plot;
    }

}
