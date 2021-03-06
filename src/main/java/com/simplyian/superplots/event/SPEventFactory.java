package com.simplyian.superplots.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class SPEventFactory {
    private final SuperPlotsPlugin main;

    public SPEventFactory(SuperPlotsPlugin main) {
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

    public PlotDisbandEvent callPlotDisbandEvent(Plot plot) {
        return callEvent(new PlotDisbandEvent(plot));
    }

    public PlotEnterEvent callPlotEnterEvent(Player player, Plot plot) {
        return callEvent(new PlotEnterEvent(player, plot));
    }

    public PlotExitEvent callPlotExitEvent(Player player, Plot plot) {
        return callEvent(new PlotExitEvent(player, plot));
    }

    public PlotInteractEvent callPlotInteractEvent(PlayerInteractEvent event,
            Plot plot) {
        return callEvent(new PlotInteractEvent(event.getPlayer(),
                event.getClickedBlock(), event.getAction(), plot));
    }

    private <T extends Event> T callEvent(T event) {
        main.getServer().getPluginManager().callEvent(event);
        return event;
    }
}
