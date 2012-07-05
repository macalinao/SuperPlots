package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionPrivate extends BaseAction {

    public ActionPrivate(SuperPlots main) {
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
                    + "You must be the owner of this plot to make it private.");
            return;
        }

        if (plot.isPrivate()) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot is already private. To make the plot public use "
                    + MsgColor.ERROR_HILIGHT + "/plot public" + MsgColor.ERROR
                    + ".");
            return;
        }

        plot.setPrivate(false);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + plot.getName()
                + MsgColor.SUCCESS + " has been made private.");
    }
}
