package com.simplyian.superplots;

import org.bukkit.Server;

import com.simplyian.superplots.actions.ActionManager;
import com.simplyian.superplots.data.DataManager;
import com.simplyian.superplots.event.SPEventFactory;
import com.simplyian.superplots.plot.PlotManager;

/**
 * SuperPlots API
 * 
 * @author simplyianm
 */
public class SuperPlots {
    private static SuperPlotsPlugin plugin;

    private SuperPlots() {

    }

    void setPlugin(SuperPlotsPlugin instance) {
        plugin = instance;
    }

    public static SuperPlotsPlugin getPlugin() {
        return plugin;
    }

    public static ActionManager getActionManager() {
        return plugin.getActionManager();
    }

    public static DataManager getDataManager() {
        return plugin.getDataManager();
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

    public static Server getServer() {
        return plugin.getServer();
    }
}
