package com.simplyian.superplots.data.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import com.simplyian.superplots.plot.PlotUpgrade;

public class JSONDataPersistor implements DataPersistor {
    private final SuperPlotsPlugin main;
    private final File plotFile;

    public JSONDataPersistor(SuperPlotsPlugin main) {
        this.main = main;
        this.plotFile = new File(main.getDataFolder(), "plots.json");
    }

    @Override
    public void savePlot(Plot plot) {
        // too slow to support
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

            JSONArray upgrades = new JSONArray();
            for (PlotUpgrade upgrade : plot.getUpgrades()) {
                upgrades.put(upgrade.name());
            }
            json.put("upgrades", upgrades);

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

            Set<PlotUpgrade> upgrades = new HashSet<PlotUpgrade>();
            JSONArray jsonUpgrades = json.getJSONArray("upgrades");
            for (int i = 0; i < jsonUpgrades.length(); i++) {
                String upgradeStr = jsonUpgrades.getString(i);
                try {
                    PlotUpgrade upgrade = PlotUpgrade.valueOf(upgradeStr);
                    upgrades.add(upgrade);
                } catch (IllegalArgumentException ex) {
                    main.getLogger().log(
                            Level.WARNING,
                            "Invalid Plot Upgrade encountered: " + upgradeStr
                                    + ". Skipping.", ex);
                    continue;
                }
            }

            Plot plot = new Plot(coowners, friends);
            plot.setName(name);
            plot.setCenter(center);
            plot.setFunds(funds);
            plot.setOwner(owner);
            plot.setPrivate(privacy);
            plot.setProtected(protect);
            plot.setSize(size);
            plot.addUpgrades(upgrades);

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
        List<Plot> list = new ArrayList<Plot>();
        if (!plotFile.exists()) {
            try {
                plotFile.createNewFile();
            } catch (IOException ex) {
                main.getLogger().log(Level.SEVERE,
                        "Could not create plot file.", ex);
                return list;
            }
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(plotFile);
        } catch (FileNotFoundException e) {
            main.getLogger().log(Level.SEVERE,
                    "Could not load plot file (File not found)", e);
            return null;
        }

        JSONObject object = new JSONObject(fis);
        Iterator<?> it = object.keys();
        while (it.hasNext()) {
            String key = it.next().toString();

            JSONObject plotJson;
            try {
                plotJson = object.getJSONObject(key);
            } catch (JSONException e) {
                main.getLogger().log(Level.SEVERE,
                        "Unable to retrieve plot '" + key + "' from JSON", e);
                continue;
            }

            Plot plot = dejsonifyPlot(plotJson);
            list.add(plot);
        }
        return list;
    }

    @Override
    public void savePlots(List<Plot> plots) {
        JSONObject json = new JSONObject();
        for (Plot plot : plots) {
            JSONObject plotJson = jsonifyPlot(plot);
            try {
                json.put(plotJson.getString("name"), plotJson);
            } catch (JSONException e) {
                main.getLogger().log(
                        Level.SEVERE,
                        "Could not insert plot '" + plot.getName()
                                + "' into JSON.", e);
                continue;
            }
        }

        if (!plotFile.exists()) {
            try {
                plotFile.createNewFile();
            } catch (IOException ex) {
                main.getLogger().log(Level.SEVERE,
                        "Could not create plot file.", ex);
                return;
            }
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(plotFile);
        } catch (FileNotFoundException e) {
            main.getLogger().log(Level.SEVERE,
                    "Could not find the plot file to save to!", e);
        }

        out.print(json.toString());
    }

}
