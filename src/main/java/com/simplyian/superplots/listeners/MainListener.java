package com.simplyian.superplots.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.event.PlotBuildEvent;
import com.simplyian.superplots.event.PlotInteractEvent;
import com.simplyian.superplots.plot.Plot;

/**
 * The main listener of the plugin.
 */
public class MainListener implements Listener {
    Map<String, Plot> pplots = new HashMap<String, Plot>();

    Map<String, Block> pblocks = new HashMap<String, Block>();

    private SuperPlotsPlugin main;

    public MainListener(SuperPlotsPlugin main) {
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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block last = pblocks.get(player.getName());
        Block now = event.getTo().getBlock();

        if (last != null && last.equals(now)) {
            return;
        }

        Plot lastPlot = pplots.get(player.getName());
        Plot plot = getPlotAt(now.getLocation());

        if (last == null) {
            pblocks.put(player.getName(), now);
            last = now;
        }

        if (lastPlot == plot) {
            return; // Moving within the same plot
        }

        pplots.put(player.getName(), plot);
        if (lastPlot == null && plot != null) {
            main.getEventFactory().callPlotEnterEvent(player, plot);
        } else if (lastPlot != null && plot == null) {
            main.getEventFactory().callPlotExitEvent(player, lastPlot);
        }
    }

    private Plot getPlotAt(Location loc) {
        return main.getPlotManager().getPlotAt(loc);
    }
}
