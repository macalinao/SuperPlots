package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.plot.Plot;

public class ActionFriend extends BaseAction {

    public ActionFriend(SuperPlots main) {
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
                    + "You must be the owner of this plot to add friends to it.");
            return;
        }

        if (args.size() <= 0) {
            player.sendMessage(MsgColor.ERROR
                    + "You didn't specify a player to add as a friend.");
            return;
        }

        String targetStr = args.get(0);
        Player target = Bukkit.getPlayer(targetStr);
        if (target == null) {
            player.sendMessage(MsgColor.ERROR
                    + "The player must be online for you to add them as a coowner.");
            return;
        }

        if (plot.isMember(target.getName())) {
            player.sendMessage(MsgColor.ERROR
                    + "The given player is already part of the plot.");
            return;
        }

        plot.addFriend(target.getName());
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + target.getName()
                + MsgColor.SUCCESS + " has become a friend of the plot.");
    }
}
