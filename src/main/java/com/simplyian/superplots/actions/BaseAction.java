package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

public abstract class BaseAction {
    /**
     * Performs the action.
     * 
     * @param player
     * @param args
     */
    public abstract void perform(Player player, List<String> args);
}
