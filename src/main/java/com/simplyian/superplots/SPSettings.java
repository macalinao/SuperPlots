package com.simplyian.superplots;

public class SPSettings {
    private final int initialPlotSize = 10;

    private final double influenceMultiplier = 1.5;

    private final double townInfluenceMultiplier = 2.0;

    private final int baseDiamonds = 1;

    private final double refundMultiplier = 0.75;

    private final int minimumPlotSize = 5;

    /**
     * @return the initialPlotSize
     */
    public int getInitialPlotSize() {
        return initialPlotSize;
    }

    /**
     * @return the influenceMultiplier
     */
    public double getInfluenceMultiplier() {
        return influenceMultiplier;
    }

    public double getTownInfluenceMultiplier() {
        return townInfluenceMultiplier;
    }

    public int getBaseDiamonds() {
        return baseDiamonds;
    }

    public double getRefundMultiplier() {
        return refundMultiplier;
    }

    public int getMinimumPlotSize() {
        return minimumPlotSize;
    }
}
