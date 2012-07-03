package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionCreate extends BaseAction {

    public ActionCreate(SuperPlots main) {
        super(main);
    }

    @Override
    public void perform(Player player, List<String> args) {
        Plot closest = main.getPlotManager().getClosestPlotAt(
                player.getLocation());
        double dist = closest.influenceEdgeDistance(player.getLocation());

        double minDist = main.getSettings().getInitialPlotSize()
                * main.getSettings().getInfluenceMultiplier();

        if (dist < minDist) {
            player.sendMessage(MsgColor.ERROR
                    + "Cannot create a plot here; the plot '"
                    + MsgColor.ERROR_HILIGHT + closest.getName()
                    + MsgColor.ERROR + "' is too close.");
            return;
        }

        String name = Joiner.on(' ').join(args);
        Plot existing = main.getPlotManager().getPlotByName(name);
        if (existing != null) {
            player.sendMessage(MsgColor.ERROR
                    + "Sorry, that name is already taken.");
            return;
        }

    }

}
