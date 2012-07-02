package com.simplyian.superplots.plot;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

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
    private Set<String> coowners;

    /**
     * Set of friends of the plot.
     */
    private Set<String> friends;

    /**
     * Funds of the plot.
     */
    private int funds;

    /**
     * Size of the plot.
     */
    private int size;

    /**
     * Center of the plot.
     */
    private Location center;

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
     * Gets the distance squared between this plot and the given location.
     * 
     * @param other
     * @return
     */
    public double distanceSquared(Location other) {
        return center.distance(other);
    }

    /**
     * Gets the distance squared between the closest edge of this plot and the
     * given location.
     * 
     * @param other
     * @return
     */
    public double edgeDistanceSquared(Location other) {
        return distanceSquared(other) - (double) size;
    }
}
