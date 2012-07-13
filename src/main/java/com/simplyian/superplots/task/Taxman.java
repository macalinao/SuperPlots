package com.simplyian.superplots.task;

import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

/**
 * Yeah, I'm the taxman.
 * 
 * @author simplyianm
 */
public class Taxman implements Runnable {
    private SuperPlotsPlugin main;

    public Taxman(SuperPlotsPlugin main) {
        this.main = main;
    }

    @Override
    public void run() {
        long last = getLastTaxTime();
        if (System.currentTimeMillis() < last + 24 * 60 * 60 * 1000) {
            return; // Check for 24 hours passage
        }

        for (Plot plot : main.getPlotManager().getPlots()) {
            int result = plot.processTax();
            if (result == 0) {
                continue;
            }

            if (result == 1) {
                // plot not disbanded
                continue;
            }

            if (result == 2) {
                // plot disbanded
                continue;
            }
        }

        main.getDataManager().getState()
                .setLastTaxTime(System.currentTimeMillis());
    }

    /**
     * Gets the last time tax happened.
     * 
     * @return
     */
    private long getLastTaxTime() {
        return main.getDataManager().getState().getLastTaxTime();
    }

}
