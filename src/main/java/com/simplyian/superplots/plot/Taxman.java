package com.simplyian.superplots.plot;

import com.simplyian.superplots.SuperPlotsPlugin;

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
        for (Plot plot : main.getPlotManager().getPlots()) {
            int result = plot.processTax();
            if (result == 0) {
                continue;
            }
        }
    }

}
