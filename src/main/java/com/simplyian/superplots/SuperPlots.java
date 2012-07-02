package com.simplyian.superplots;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * SuperPlots main plugin class.
 * @author simplyianm
 */
public class SuperPlots extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "SuperPlots enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "SuperPlots disabled!");
    }
}
