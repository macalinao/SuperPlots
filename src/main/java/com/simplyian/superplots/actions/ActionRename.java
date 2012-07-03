package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionRename extends BaseAction {

    public ActionRename(SuperPlots main) {
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
                    + "You must be the owner of this plot to rename it.");
            return;
        }

        String newName = Joiner.on(' ').join(args);
        if (newName.length() <= 0) {
            player.sendMessage(MsgColor.ERROR + "You did not specify a name.");
            return;
        }

        if (!Plot.isValidName(newName)) {
            player.sendMessage(MsgColor.ERROR
                    + "The name you have given is invalid.");
            player.sendMessage(MsgColor.ERROR
                    + "Names may only contain letters, numbers, spaces, apostrophes, and exclamation points.");
            player.sendMessage(MsgColor.ERROR
                    + "Names can only be 40 letters long.");
            return;
        }

        Plot existing = main.getPlotManager().getPlotByName(newName);
        if (existing != null) {
            player.sendMessage(MsgColor.ERROR
                    + "Sorry, that name is already taken.");
            return;
        }

        plot.setName(newName);
        player.sendMessage(MsgColor.SUCCESS + "The plot was renamed.");
    }
}
