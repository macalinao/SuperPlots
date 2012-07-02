package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

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
        
        
    }

}
