package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionKick extends BaseAction {

    public ActionKick(SuperPlots main) {
        super(main);
    }

    @Override
    public void perform(Player player, List<String> args) {
        Plot plot = main.getPlotManager().getPlotAt(player.getLocation());
        if (plot == null) {
            player.sendMessage(MsgColor.ERROR + "You are not in a plot.");
            return;
        }

        if (!plot.isAdministrator(player.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "You must be a coowner or owner of the plot to kick people from it.");
            return;
        }

        if (args.size() <= 0) {
            player.sendMessage(MsgColor.ERROR
                    + "You didn't specify a player to kick.");
            return;
        }

        String targetStr = args.get(0);
        Player target = Bukkit.getPlayer(targetStr);
        if (target == null) {
            player.sendMessage(MsgColor.ERROR
                    + "The player must be online for you to kick them from the plot.");
            return;
        }

        if (!plot.isMember(target.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "The given player is not a member of the plot.");
        }

        if (plot.isCoowner(target.getName()) && !plot.isOwner(player.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "You can't kick other coowners from the plot; only owners can.");
        }

        if (plot.isOwner(target.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "You can't kick the owner out of their own plot!");
        }

        plot.kick(target.getName());
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + target.getName()
                + MsgColor.SUCCESS + " has been kicked from the plot.");
    }
}
