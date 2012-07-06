package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionWithdraw extends BaseAction {

    public ActionWithdraw(SuperPlotsPlugin main) {
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
                    + "You must be an administrator of this plot to withdraw dubloons from it.");
            return;
        }

        if (args.size() <= 0) {
            player.sendMessage(MsgColor.ERROR
                    + "You did not specify an amount of money to withdraw.");
            return;
        }

        String amountStr = args.get(0);
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException ex) {
            player.sendMessage(MsgColor.ERROR_HILIGHT + amountStr
                    + MsgColor.ERROR
                    + " is not a valid amount of money to withdraw.");
            return;
        }

        int balance = plot.getFunds();
        if (balance < amount) {
            player.sendMessage(MsgColor.ERROR
                    + "The plot doesn't have that much money; it only has "
                    + MsgColor.ERROR_HILIGHT + "D" + balance + MsgColor.ERROR
                    + ".");
            return;
        }

        plot.subtractFunds(amount);
        main.getEconomy().addBalance(player.getName(), amount);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + "D" + amount
                + MsgColor.SUCCESS + " has been withdrawn from "
                + MsgColor.SUCCESS_HILIGHT + plot.getName() + MsgColor.SUCCESS
                + " and put into your wallet.");
    }
}
