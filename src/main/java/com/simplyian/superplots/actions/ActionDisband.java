package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionDisband extends BaseAction {

    public ActionDisband(SuperPlotsPlugin main) {
        super(main, "disband");
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

        int dubloons = (int) (plot.getValue() * main.getSettings()
                .getRefundMultiplier());
        main.getEconomy().addBalance(player.getName(), dubloons);
        plot.disband();
        player.sendMessage(MsgColor.SUCCESS
                + "The plot was disbanded. You have received D" + dubloons
                + " as a result.");
    }
}
