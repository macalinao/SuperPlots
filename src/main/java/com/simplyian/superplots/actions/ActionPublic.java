package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionPublic extends BaseAction {

    public ActionPublic(SuperPlotsPlugin main) {
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
                    + "You must be the owner of this plot to make it public.");
            return;
        }

        if (plot.isPublic()) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot is already public. To make the plot private use "
                    + MsgColor.ERROR_HILIGHT + "/plot private" + MsgColor.ERROR
                    + ".");
            return;
        }

        plot.setPrivate(false);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + plot.getName()
                + MsgColor.SUCCESS + " has been made public.");
    }
}
