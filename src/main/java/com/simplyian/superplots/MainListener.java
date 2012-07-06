package com.simplyian.superplots;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.simplyian.superplots.event.PlotBuildEvent;
import com.simplyian.superplots.event.PlotInteractEvent;
import com.simplyian.superplots.plot.Plot;

/**
 * The main listener of the plugin.
 */
public class MainListener implements Listener {
    private SuperPlots main;

    public MainListener(SuperPlots main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Plot plot = getPlotAt(event.getBlock().getLocation());
        if (plot == null) {
            return;
        }
        PlotBuildEvent ev = main.getEventFactory().callPlotBuildEvent(event,
                plot);
        if (ev.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Plot plot = getPlotAt(event.getBlock().getLocation());
        if (plot == null) {
            return;
        }
        PlotBuildEvent ev = main.getEventFactory().callPlotBuildEvent(event,
                plot);
        if (ev.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Plot plot = getPlotAt(block.getLocation());
        if (plot == null) {
            return;
        }

        PlotInteractEvent ev = main.getEventFactory().callPlotInteractEvent(
                event, plot);
        if (ev.isCancelled()) {
            event.setCancelled(true);
        }
    }

    private Plot getPlotAt(Location loc) {
        return main.getPlotManager().getPlotAt(loc);
    }
}
