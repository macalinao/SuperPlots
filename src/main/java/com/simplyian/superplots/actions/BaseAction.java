package com.simplyian.superplots.actions;

import java.util.List;

import org.bukkit.entity.Player;

import com.simplyian.superplots.SuperPlotsPlugin;

public abstract class BaseAction {
    protected final SuperPlotsPlugin main;

    private String name;

    public BaseAction(SuperPlotsPlugin main, String name) {
        this.main = main;
        this.name = name;
    }

    /**
     * Gets the name of the action.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Performs the action.
     * 
     * @param player
     * @param args
     */
    public abstract void perform(Player player, List<String> args);
}
