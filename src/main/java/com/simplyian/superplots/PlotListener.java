package com.simplyian.superplots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.simplyian.superplots.event.PlotInteractEvent;
import com.simplyian.superplots.plot.Plot;

/**
 * The listener that handles plot events.
 * 
 * @author simplyianm
 */
public class PlotListener implements Listener {
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
