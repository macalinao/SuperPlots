package com.simplyian.superplots.event;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class SPEventFactory {
    private final SuperPlots main;

    public SPEventFactory(SuperPlots main) {
        this.main = main;
    }

    public PlotBuildEvent callPlotBuildEvent(BlockBreakEvent event, Plot plot) {

        PlotBuildEvent ret = new PlotBuildEvent(plot, false, event.getPlayer(),
                event.getBlock());
        return callEvent(ret);
    }

    public PlotBuildEvent callPlotBuildEvent(BlockPlaceEvent event, Plot plot) {

        PlotBuildEvent ret = new PlotBuildEvent(plot, false, event.getPlayer(),
                event.getBlock());
        return callEvent(ret);
    }

    public PlotCreateEvent callPlotCreateEvent(Plot plot) {
        return callEvent(new PlotCreateEvent(plot));
    }

    public PlotDestroyEvent callPlotDestroyEvent(Plot plot) {
        return callEvent(new PlotDestroyEvent(plot));
    }

    private <T extends Event> T callEvent(T event) {
        main.getServer().getPluginManager().callEvent(event);
        return event;
    }
}
