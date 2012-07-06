package com.simplyian.superplots;

import com.simplyian.superplots.event.SPEventFactory;
import com.simplyian.superplots.plot.PlotManager;

/**
 * SuperPlots API
 * 
 * @author simplyianm
 */
public class SuperPlots {
    private static SuperPlotsPlugin plugin;

    void setPlugin(SuperPlotsPlugin instance) {
        plugin = instance;
    }

    public static EconHook getEconomy() {
        return plugin.getEconomy();
    }

    public static PlotManager getPlotManager() {
        return plugin.getPlotManager();
    }

    public static SPEventFactory getEventFactory() {
        return plugin.getEventFactory();
    }

    public static SPSettings getSettings() {
        return plugin.getSettings();
    }
}
