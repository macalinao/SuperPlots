package com.simplyian.superplots.data.persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

public class JSONDataPersistor implements DataPersistor {
    private final SuperPlotsPlugin main;

    public JSONDataPersistor(SuperPlotsPlugin main) {
        this.main = main;
    }

    @Override
    public void savePlot(Plot plot) {
        JSONObject json = jsonifyPlot(plot);
    }

    /**
     * Converts a plot to JSON.
     * 
     * @param plot
     * @return
     */
    public JSONObject jsonifyPlot(Plot plot) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", plot.getName());
            json.put("owner", plot.getOwner());

            JSONArray coowners = new JSONArray();
            for (String coowner : plot.getCoowners()) {
                coowners.put(coowner);
            }
            json.put("coowners", coowners);

            JSONArray friends = new JSONArray();
            for (String friend : plot.getFriends()) {
                friends.put(friend);
            }
            json.put("friends", friends);

            json.put("funds", plot.getFunds());
            json.put("size", plot.getSize());

            JSONObject center = new JSONObject();
            center.put("world", plot.getCenter().getWorld().getName());
            center.put("x", plot.getCenter().getBlockX());
            center.put("y", plot.getCenter().getBlockY());
            center.put("z", plot.getCenter().getBlockZ());
            json.put("center", center);

            json.put("protect", plot.isProtected());
            json.put("privacy", plot.isPrivate());

        } catch (JSONException e) {
            SuperPlotsPlugin.getInstance().getLogger()
                    .log(Level.SEVERE, "Could not serialize plot to JSON!", e);
            return null;
        }
        return json;
    }

    /**
     * Converts JSON to a plot.
     * 
     * @param json
     * @return
     */
    public Plot dejsonifyPlot(JSONObject json) {
        try {
            String name = json.getString("name");
            String owner = json.getString("owner");

            Set<String> coowners = new HashSet<String>();
            JSONArray jsonCoowners = json.getJSONArray("coowners");
            for (int i = 0; i < jsonCoowners.length(); i++) {
                coowners.add(jsonCoowners.getString(i));
            }

            Set<String> friends = new HashSet<String>();
            JSONArray jsonFriends = json.getJSONArray("friends");
            for (int i = 0; i < jsonFriends.length(); i++) {
                friends.add(jsonFriends.getString(i));
            }

            int funds = json.getInt("funds");
            int size = json.getInt("size");

            JSONObject jsonCenter = json.getJSONObject("center");
            String worldstr = jsonCenter.getString("world");
            int x = jsonCenter.getInt("x");
            int y = jsonCenter.getInt("y");
            int z = jsonCenter.getInt("z");
            World world = SuperPlots.getServer().getWorld(worldstr);
            Location center = new Location(world, x, y, z);

            boolean protect = json.getBoolean("protect");
            boolean privacy = json.getBoolean("privacy");

            Plot plot = new Plot(coowners, friends);
            plot.setName(name);
            plot.setCenter(center);
            plot.setFunds(funds);
            plot.setOwner(owner);
            plot.setPrivate(privacy);
            plot.setProtected(protect);
            plot.setSize(size);

            return plot;
        } catch (JSONException e) {
            SuperPlotsPlugin
                    .getInstance()
                    .getLogger()
                    .log(Level.SEVERE, "Could not deserialize plot from JSON!",
                            e);
            return null;
        }
    }

    @Override
    public List<Plot> loadPlots() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void savePlots(List<Plot> plots) {
        // TODO Auto-generated method stub

    }

}
