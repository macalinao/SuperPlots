package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotUpgrade;

public class ActionUpgrade extends BaseAction {

    public ActionUpgrade(SuperPlotsPlugin main) {
        super(main, "upgrade");
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
                    + "You must be the owner of this plot to upgrade it.");
            return;
        }

        if (args.size() <= 0) {
            player.sendMessage(MsgColor.ERROR
                    + "You must specify a plot upgrade to apply.");
            return;
        }

        String upgradeS = Joiner.on(' ').join(args).toUpperCase().trim()
                .replace(' ', '_');
        PlotUpgrade upgrade;
        try {
            upgrade = PlotUpgrade.valueOf(upgradeS);
        } catch (IllegalArgumentException ex) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot upgrade you specified does not exist. Available upgrades:");
            player.sendMessage(MsgColor.ERROR_HILIGHT
                    + Joiner.on(", ").join(PlotUpgrade.values()));
            return;
        }

        if (plot.has(upgrade)) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot already has that upgrade.");
            return;
        }

        if (plot.getFunds() < upgrade.getCost()) {
            player.sendMessage(MsgColor.ERROR
                    + "You don't have enough money to buy the "
                    + upgrade.name() + " upgrade. (Costs D" + upgrade.getCost()
                    + "; the plot only has D" + plot.getFunds() + ")");
            return;
        }

        plot.subtractFunds(upgrade.getCost());
        plot.addUpgrade(upgrade);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + plot.getName()
                + MsgColor.SUCCESS + " has been given the " + upgrade.name()
                + " upgrade.");
    }
}
