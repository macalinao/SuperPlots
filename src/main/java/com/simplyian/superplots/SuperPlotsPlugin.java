package com.simplyian.superplots;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.simplyian.superplots.actions.ActionManager;
import com.simplyian.superplots.command.PlotCommand;
import com.simplyian.superplots.data.DataManager;
import com.simplyian.superplots.event.SPEventFactory;
import com.simplyian.superplots.listeners.MainListener;
import com.simplyian.superplots.listeners.PlotListener;
import com.simplyian.superplots.plot.PlotManager;
import com.simplyian.superplots.plot.Taxman;

/**
 * SuperPlots main plugin class.
 * 
 * @author simplyianm
 */
public class SuperPlotsPlugin extends JavaPlugin {
    private static SuperPlotsPlugin instance;

    private ActionManager actionManager;

    private DataManager dataManager;

    /**
     * Hook into the economy.
     */
    private EconHook economy;

    /**
     * The plot manager.
     */
    private PlotManager plotManager;

    private SPEventFactory eventFactory;

    private SPSettings settings;

    public SuperPlotsPlugin() {
        instance = this;
    }

    public static SuperPlotsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger()
                .log(Level.INFO, "================ Begin SuperPlots enable!");

        getLogger().log(Level.INFO, "Setting up action manager...");
        actionManager = new ActionManager(this);

        getLogger().log(Level.INFO, "Setting up commands...");
        setupCommands();

        getLogger().log(Level.INFO, "Setting up economy...");
        economy = new EconHook(this);
        economy.setupEconomy();

        getLogger().log(Level.INFO, "Setting up plots...");
        plotManager = new PlotManager(this);
        getLogger().log(Level.INFO, "  Loading plots...");
        plotManager.loadAll();

        getLogger().log(Level.INFO, "Setting up events...");
        eventFactory = new SPEventFactory(this);

        getLogger().log(Level.INFO, "Setting up event listeners...");
        setupListeners();

        getLogger().log(Level.INFO, "Setting up settings...");
        settings = new SPSettings();

        getLogger().log(Level.INFO, "Initializing taxman...");
        getServer().getScheduler().scheduleSyncRepeatingTask(this,
                new Taxman(this), 0L, 20 * 60L);

        getLogger().log(Level.INFO, "================= SuperPlots enabled!");
    }

    @Override
    public void onDisable() {
        economy = null;
        plotManager = null;
        eventFactory = null;
        settings = null;
        getLogger().log(Level.INFO, "SuperPlots disabled!");
    }

    private void setupCommands() {
        getCommand("plot").setExecutor(new PlotCommand(this));
    }

    private void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new MainListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlotListener(this), this);
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    /**
     * @return the economy
     */
    public EconHook getEconomy() {
        return economy;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }

    public SPEventFactory getEventFactory() {
        return eventFactory;
    }

    public SPSettings getSettings() {
        return settings;
    }
}
