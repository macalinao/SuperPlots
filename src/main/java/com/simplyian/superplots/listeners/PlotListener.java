package com.simplyian.superplots.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.event.PlotBuildEvent;
import com.simplyian.superplots.event.PlotEnterEvent;
import com.simplyian.superplots.event.PlotExitEvent;
import com.simplyian.superplots.event.PlotInteractEvent;
import com.simplyian.superplots.plot.Plot;

/**
 * The listener that handles plot events.
 * 
 * @author simplyianm
 */
public class PlotListener implements Listener {
    private SuperPlotsPlugin main;

    public PlotListener(SuperPlotsPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onPlotBuild(PlotBuildEvent event) {
        if (event.getPlot().isMember(event.getPlayer().getName())) {
            return;
        }

        event.getPlayer().sendMessage(
                MsgColor.ERROR + "You aren't allowed to build in "
                        + MsgColor.ERROR_HILIGHT + event.getPlot().getName()
                        + MsgColor.ERROR + ".");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlotEnter(PlotEnterEvent event) {
        event.getPlayer().sendMessage(
                MsgColor.INFO + "You have entered "
                        + MsgColor.INFO_HILIGHT + event.getPlot().getName()
                        + MsgColor.INFO + ".");
    }

    @EventHandler
    public void onPlotExit(PlotExitEvent event) {
        event.getPlayer().sendMessage(
                MsgColor.INFO + "You have left "
                        + MsgColor.INFO_HILIGHT + event.getPlot().getName()
                        + MsgColor.INFO + " and have entered wilderness.");
    }

    @EventHandler
    public void onPlotInteract(PlotInteractEvent event) {
        Player player = event.getPlayer();
        Plot plot = event.getPlot();
        if (plot.isMember(player.getName())) {
            return;
        }

        Material mat = event.getBlock().getType();
        if (!mat.equals(Material.LEVER) && !mat.equals(Material.STONE_BUTTON)) {
            return;
        }

        player.sendMessage(MsgColor.ERROR
                + "You aren't allowed to use levers or buttons in "
                + MsgColor.ERROR_HILIGHT + plot.getName() + MsgColor.ERROR
                + ".");
        event.setCancelled(true);
    }
}
