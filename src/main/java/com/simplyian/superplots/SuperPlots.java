package com.simplyian.superplots;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.simplyian.superplots.plot.PlotManager;

/**
 * SuperPlots main plugin class.
 * 
 * @author simplyianm
 */
public class SuperPlots extends JavaPlugin {
    /**
     * The plot manager.
     */
    private PlotManager plotManager;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Setting up event listeners...");
        setupListeners();
        getLogger().log(Level.INFO, "SuperPlots enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "SuperPlots disabled!");
    }

    private void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new MainListener(this), this);
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }
}
