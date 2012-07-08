package com.simplyian.superplots.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.actions.BaseAction;

public class PlotCommand implements CommandExecutor {
    private SuperPlotsPlugin main;

    public PlotCommand(SuperPlotsPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MsgColor.ERROR
                    + "You can only use this command ingame.");
            return true;
        }

        Player player = (Player) sender;
        List<String> argv = Lists.newArrayList(args);

        if (argv.size() <= 0) {
            sendHelp(player, null);
            return true;
        }

        String action = argv.get(0);
        argv.remove(0);

        BaseAction act = main.getActionManager().getAction(action);
        if (act == null) {
            sendHelp(player, action);
            return true;
        }

        act.perform(player, argv);
        return true;
    }

    /**
     * Sends help to the given player.
     * 
     * @param player
     * @param arg
     */
    public void sendHelp(Player player, String arg) {
        if (arg != null) {
            player.sendMessage(MsgColor.ERROR + "There is no such command as '"
                    + MsgColor.ERROR_HILIGHT + arg + MsgColor.ERROR
                    + "'. Available commands:");
        } else {
            player.sendMessage(MsgColor.ERROR
                    + "You didn't specify a command. To use one type "
                    + MsgColor.ERROR_HILIGHT + "/plot thecommand"
                    + MsgColor.ERROR + ". Available commands:");
        }

        List<BaseAction> actions = main.getActionManager().getActions();

        if (actions.size() == 0) {
            return;
        }

        StringBuilder builder = new StringBuilder(actions.get(0).getName());
        actions.remove(0);
        for (BaseAction action : actions) {
            builder.append(", ").append(action.getName());
        }
        player.sendMessage(MsgColor.ERROR_HILIGHT + builder.toString());
    }

}
