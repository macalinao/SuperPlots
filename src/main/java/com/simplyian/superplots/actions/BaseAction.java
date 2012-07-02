package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.SuperPlots;

public abstract class BaseAction {
    protected final SuperPlots main;

    public BaseAction(SuperPlots main) {
        this.main = main;
    }

    /**
     * Performs the action.
     * 
     * @param player
     * @param args
     */
    public abstract void perform(Player player, List<String> args);
}
