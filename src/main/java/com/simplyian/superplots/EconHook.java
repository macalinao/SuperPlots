package com.simplyian.superplots;

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconHook {
    private final SuperPlots main;
    private Economy economy = null;

    public EconHook(SuperPlots main) {
        this.main = main;
    }

    public void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = main.getServer()
                .getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        if (economy == null) {
            main.getLogger()
                    .log(Level.SEVERE,
                            "No supported economy by Vault detected! Things WILL go wrong!");
        }
    }

    /**
     * Gets the balance of a player.
     * 
     * @param player
     * @return
     */
    public double getBalance(String player) {
        return economy.getBalance(player);
    }

    /**
     * Sets the balance of a player.
     * 
     * @param player
     * @param amt
     */
    public void setBalance(String player, double amt) {
        economy.depositPlayer(player, amt);
    }

    public void addBalance(String player, int dubloons) {
        // TODO Auto-generated method stub

    }

    public void subtractBalance(String name, int cost) {
        // TODO Auto-generated method stub
        
    }
}
