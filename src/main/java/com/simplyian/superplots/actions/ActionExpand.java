package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionExpand extends BaseAction {

    public ActionExpand(SuperPlotsPlugin main) {
        super(main);
    }

    @Override
    public void perform(Player player, List<String> args) {
        Plot plot = main.getPlotManager().getPlotAt(player.getLocation());
        if (plot == null) {
            player.sendMessage(MsgColor.ERROR + "You are not in a plot.");
            return;
        }

        if (!plot.isOwner(player.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "You must be the owner of this plot to expand it.");
            return;
        }

        Plot closest = main.getPlotManager().getClosestPlotAt(
                player.getLocation(), plot);
        int size = plot.getSize() + 1;
        if (closest != null && closest.influenceEdgeDistance(player.getLocation()) < size) {
            player.sendMessage(MsgColor.ERROR
                    + "You can't expand your plot any further; you are too close to "
                    + MsgColor.ERROR_HILIGHT + closest.getName()
                    + MsgColor.ERROR + ".");
        }

        int cost = plot.getSize() * 10;
        int onHand = plot.getFunds();
        if (onHand < cost) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot doesn't have enough money to expand. (Costs D"
                    + cost + ")");
            return;
        }

        plot.subtractFunds(cost);
        plot.expand();
        player.sendMessage(MsgColor.SUCCESS
                + "The plot was expanded from size " + (size - 1) + " to "
                + size + ". As a result, " + cost
                + " dubloons have been taken from the plot funds.");
    }
}
