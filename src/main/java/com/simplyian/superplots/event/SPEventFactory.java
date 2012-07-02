package com.simplyian.superplots.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.simplyian.superplots.plot.Plot;

public class SPEventFactory {
    public static PlotBuildEvent callPlotBuildEvent(BlockBreakEvent event,
            Plot plot) {

        PlotBuildEvent ret = new PlotBuildEvent(plot, false, event.getPlayer(),
                event.getBlock());
        return callEvent(ret);
    }

    public static PlotBuildEvent callPlotBuildEvent(BlockPlaceEvent event,
            Plot plot) {

        PlotBuildEvent ret = new PlotBuildEvent(plot, false, event.getPlayer(),
                event.getBlock());
        return callEvent(ret);
    }

    private static <T extends Event> T callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
}
