package com.simplyian.superplots.plot;

/**
 * Contains different plot upgrades.
 * 
 * @author simplyianm
 */
public enum PlotUpgrade {
    /**
     * Town upgrade.
     */
    TOWN(50000);

    private int cost;

    PlotUpgrade(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the cost of this upgrade.
     * 
     * @return
     */
    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name();
    }
}
