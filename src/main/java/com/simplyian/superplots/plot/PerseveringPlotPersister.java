package com.simplyian.superplots.plot;

import java.util.logging.Level;

import com.simplyian.superplots.SuperPlotsPlugin;

/**
 * AKA PPP
 * 
 * @author simplyianm
 */
public class PerseveringPlotPersister implements Runnable {
    private final SuperPlotsPlugin main;

    public PerseveringPlotPersister(SuperPlotsPlugin main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getLogger().log(Level.INFO,
                "Saving all plots, hang on to your hard drives!");
        main.getPlotManager().saveAll();
        main.getLogger().log(Level.INFO, "Saving complete.");
    }

}
