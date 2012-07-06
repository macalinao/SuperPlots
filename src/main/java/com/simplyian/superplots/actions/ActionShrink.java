package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionShrink extends BaseAction {

    public ActionShrink(SuperPlotsPlugin main) {
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
                    + "You must be the owner of this plot to shrink it.");
            return;
        }

        int amount = 1;
        if (args.size() > 0) {
            try {
                amount = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ex) {
                player.sendMessage(MsgColor.ERROR + "'" + args.get(0)
                        + "' is not a valid number to shrink your plot by.");
                return;
            }
        }

        int initialSize = plot.getSize();
        int size = initialSize - amount;

        if (size < main.getSettings().getMinimumPlotSize()) {
            player.sendMessage(MsgColor.ERROR
                    + "You cannot shrink your plot that much; the minimum size of a plot is "
                    + main.getSettings().getMinimumPlotSize() + ".");
            return;
        }

        plot.shrink(amount);

        player.sendMessage(MsgColor.SUCCESS
                + "The plot has been shrunk from size " + initialSize + " to "
                + size + ".");
    }
}