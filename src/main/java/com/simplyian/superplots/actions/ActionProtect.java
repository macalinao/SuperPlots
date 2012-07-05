package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionProtect extends BaseAction {

    public ActionProtect(SuperPlots main) {
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
                    + "You must be the owner of this plot to disband it.");
            return;
        }

        if (plot.isProtected()) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot is already protected. To turn off protection use "
                    + MsgColor.ERROR_HILIGHT + "/plot unprotect"
                    + MsgColor.ERROR + ".");
            return;
        }

        plot.setProtected(true);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + plot.getName()
                + MsgColor.SUCCESS + " has been protected.");
    }
}
