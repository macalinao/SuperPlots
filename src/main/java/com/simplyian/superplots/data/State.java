package com.simplyian.superplots.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import com.simplyian.superplots.SuperPlotsPlugin;

/**
 * Holds the current state of the plugin.
 * 
 * @author simplyianm
 */
public class State {
    private SuperPlotsPlugin main;
    private File file;
    private YamlConfiguration config;

    public State(SuperPlotsPlugin main, File file) {
        this.main = main;
        this.file = file;

        loadState();
    }

    /**
     * Gets the last time tax occurred.
     * 
     * @return
     */
    public long getLastTaxTime() {
        return config.getLong("lasttax", 0L);
    }

    /**
     * Sets the last time of tax.
     * 
     * @param time
     *            The time to set
     */
    public void setLastTaxTime(long time) {
        config.set("lasttax", time);
        saveState();
    }

    /**
     * Loads the configuration.
     */
    private void loadState() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                main.getLogger()
                        .log(Level.SEVERE,
                                "State file could not be created! Unknown things may occur!");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves the configuration.
     */
    private void saveState() {
        try {
            config.save(file);
        } catch (IOException e) {
            main.getLogger().log(Level.SEVERE,
                    "Oh no! Config couldn't be saved.");
        }
    }

}
