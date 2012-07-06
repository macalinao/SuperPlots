package com.simplyian.superplots.data;

import java.io.File;

import com.simplyian.superplots.SuperPlotsPlugin;

/**
 * Manages everything but data.
 * 
 * @author simplyianm
 */
public class DataManager {
    private SuperPlotsPlugin main;
    private State state;

    public DataManager(SuperPlotsPlugin main) {
        this.main = main;
        state = new State(main, new File(main.getDataFolder(), "state.yml"));
    }

    /**
     * Gets the state.
     * 
     * @return
     */
    public State getState() {
        return state;
    }
}
