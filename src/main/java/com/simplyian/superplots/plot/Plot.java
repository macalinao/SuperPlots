package com.simplyian.superplots.plot;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import com.simplyian.superplots.SuperPlots;

/**
 * Represents an ownable piece of land.
 */
public class Plot {
    /**
     * Name of the plot.
     */
    private String name;

    /**
     * Name of the owner of the plot.
     */
    private String owner;

    /**
     * Set of coowners of the plot.
     */
    private Set<String> coowners = new HashSet<String>();

    /**
     * Set of friends of the plot.
     */
    private Set<String> friends = new HashSet<String>();

    /**
     * Funds of the plot.
     */
    private int funds = 0;

    /**
     * Size of the plot.
     */
    private int size;

    /**
     * Center of the plot.
     */
    private Location center;

    /**
     * @see PlotManager#createPlot
     */
    Plot(String name, String owner, int size, Location center) {
        this.name = name;
        this.owner = owner;
        this.size = size;
        this.center = center;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Checks if the given player is the owner of the plot.
     * 
     * @param owner
     * @return
     */
    public boolean isOwner(String owner) {
        return getOwner().equals(owner);
    }

    /**
     * Adds a coowner to the plot.
     * 
     * @param coowner
     */
    public void addCoowner(String coowner) {
        coowners.add(coowner);
    }

    /**
     * Removes a coowner from the plot.
     * 
     * @param coowner
     */
    public void removeCoowner(String coowner) {
        coowners.remove(coowner);
    }

    /**
     * Gets a set of all coowners of this plot.
     * 
     * @return
     */
    public Set<String> getCoowners() {
        return new HashSet<String>(coowners);
    }

    /**
     * Checks if the given player is a coowner.
     * 
     * @param coowner
     * @return
     */
    public boolean isCoowner(String coowner) {
        return coowners.contains(coowner);
    }

    /**
     * Adds a friend to the plot.
     * 
     * @param friend
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Removes a friend from the plot.
     * 
     * @param friend
     */
    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    /**
     * Checks if the given player is a friend.
     * 
     * @param friend
     * @return
     */
    public boolean isFriend(String friend) {
        return friends.contains(friend);
    }

    /**
     * Gets the friends of this plot.
     * 
     * @return
     */
    public Set<String> getFriends() {
        return new HashSet<String>(friends);
    }

    /**
     * @return the funds
     */
    public int getFunds() {
        return funds;
    }

    /**
     * @param funds
     *            the funds to set
     */
    public void setFunds(int funds) {
        this.funds = funds;
    }

    /**
     * Adds funds to the plot.
     * 
     * @param funds
     */
    public void addFunds(int funds) {
        setFunds(getFunds() + funds);
    }

    /**
     * Subtracts funds from the plot.
     * 
     * @param funds
     */
    public void subtractFunds(int funds) {
        setFunds(getFunds() - funds);
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the center
     */
    public Location getCenter() {
        return center;
    }

    /**
     * @param center
     *            the center to set
     */
    public void setCenter(Location center) {
        this.center = center;
    }

    /**
     * Gets the distance between this plot and the given location.
     * 
     * @param other
     * @return
     */
    public double distance(Location other) {
        return center.distance(other);
    }

    /**
     * Gets the distance squared between this plot and the given location.
     * 
     * @param other
     * @return
     */
    public double distanceSquared(Location other) {
        return center.distanceSquared(other);
    }

    /**
     * Gets the distance between the closest edge of this plot and the given
     * location.
     * 
     * @param other
     * @return
     */
    public double edgeDistance(Location other) {
        return distance(other) - (double) getSize();
    }

    /**
     * Gets the distance between the closest edge of the influence of this plot
     * and the given location.
     * 
     * @param location
     * @return
     */
    public double influenceEdgeDistance(Location location) {
        return edgeDistance(location)
                * SuperPlots.getInstance().getSettings()
                        .getInfluenceMultiplier();
    }

    /**
     * Expands the plot by one.
     */
    public void expand() {
        expand(1);
    }

    /**
     * Expands the plot by the given amount.
     * 
     * @param amount
     */
    public void expand(int amount) {
        setSize(getSize() + amount);
    }

    /**
     * Shrinks the plot by the given amount.
     * 
     * @param amount
     */
    public void shrink(int amount) {
        setSize(getSize() - amount);
    }

    /**
     * Returns true if the plot contains the given location.
     * 
     * @param location
     * @return
     */
    public boolean contains(Location location) {
        return distanceSquared(location) < (getSize() * getSize());
    }

    /**
     * Gets the value of the plot.
     * 
     * @return
     */
    public int getValue() {
        int val = 0;
        for (int i = 1; i <= getSize(); i++) {
            val += i * 10;
        }
        return val;
    }

    /**
     * Returns true if the name is valid.
     * 
     * @param name
     * @return
     */
    public static boolean isValidName(String name) {
        return name.matches("[A-Za-z0-9 '!\\.]{1,40}");
    }
}
