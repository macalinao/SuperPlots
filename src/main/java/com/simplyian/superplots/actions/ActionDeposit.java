package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class ActionDeposit extends BaseAction {

    public ActionDeposit(SuperPlotsPlugin main) {
        super(main);
    }

    @Override
    public void perform(Player player, List<String> args) {
        Plot plot = main.getPlotManager().getPlotAt(player.getLocation());
        if (plot == null) {
            player.sendMessage(MsgColor.ERROR + "You are not in a plot.");
            return;
        }

        if (args.size() <= 0) {
            player.sendMessage(MsgColor.ERROR
                    + "You did not specify an amount of money to deposit.");
            return;
        }

        String amountStr = args.get(0);
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException ex) {
            player.sendMessage(MsgColor.ERROR_HILIGHT + amountStr
                    + MsgColor.ERROR
                    + " is not a valid amount of money to deposit.");
            return;
        }

        double balance = main.getEconomy().getBalance(player.getName());
        if (balance < amount) {
            player.sendMessage(MsgColor.ERROR
                    + "You don't have that much money; you only have "
                    + MsgColor.ERROR_HILIGHT + "D" + balance + MsgColor.ERROR
                    + ".");
            return;
        }

        plot.addFunds(amount);
        main.getEconomy().subtractBalance(player.getName(), amount);
        player.sendMessage(MsgColor.SUCCESS_HILIGHT + "D" + amount
                + MsgColor.SUCCESS + " have been deposited into "
                + MsgColor.SUCCESS_HILIGHT + plot.getName() + MsgColor.SUCCESS
                + ".");
    }
}
