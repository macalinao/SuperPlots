package com.simplyian.superplots.data.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Sets;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SuperPlots.class)
public class JSONDataPersistorTest {
    private SuperPlotsPlugin plugin;
    private Server server;
    private JSONDataPersistor instance;

    @Before
    public void setup() {
        plugin = mock(SuperPlotsPlugin.class);
        mockStatic(SuperPlots.class);
        when(SuperPlots.getPlugin()).thenReturn(plugin);

        server = mock(Server.class);
        when(SuperPlots.getServer()).thenReturn(server);

        instance = new JSONDataPersistor(plugin);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    @Test
    public void test_jsonifyPlot() {
        Plot plot = mock(Plot.class);
        when(plot.getName()).thenReturn("my plot");
        when(plot.getOwner()).thenReturn("owner of plot");

        World world = mock(World.class);
        when(world.getName()).thenReturn("buckingham");

        Location center = mock(Location.class);
        when(center.getWorld()).thenReturn(world);
        when(center.getBlockX()).thenReturn(12);
        when(center.getBlockY()).thenReturn(23);
        when(center.getBlockZ()).thenReturn(34);

        when(plot.getCenter()).thenReturn(center);

        Set<String> coowners = Sets.newHashSet("kyoko", "akari", "bobby");
        when(plot.getCoowners()).thenReturn(coowners);

        Set<String> friends = Sets.newHashSet("akaza", "toshino", "yui");
        when(plot.getFriends()).thenReturn(friends);

        when(plot.getFunds()).thenReturn(525600);
        when(plot.getSize()).thenReturn(10);
        when(plot.isProtected()).thenReturn(true);
        when(plot.isPrivate()).thenReturn(false);

        JSONObject result = instance.jsonifyPlot(plot);
        try {
            assertEquals("my plot", result.getString("name"));
            assertEquals("owner of plot", result.getString("owner"));

            JSONObject centerO = result.getJSONObject("center");
            assertEquals("buckingham", centerO.getString("world"));
            assertEquals(12, centerO.getInt("x"));
            assertEquals(23, centerO.getInt("y"));
            assertEquals(34, centerO.getInt("z"));

            JSONArray coownersA = result.getJSONArray("coowners");
            Set<String> coownersS = new HashSet<String>();
            for (int i = 0; i < coownersA.length(); i++) {
                coownersS.add(coownersA.getString(i));
            }
            assertTrue(coownersS.contains("kyoko"));
            assertTrue(coownersS.contains("akari"));
            assertTrue(coownersS.contains("bobby"));

            JSONArray friendsA = result.getJSONArray("friends");
            Set<String> friendsS = new HashSet<String>();
            for (int i = 0; i < friendsA.length(); i++) {
                friendsS.add(friendsA.getString(i));
            }
            assertTrue(friendsS.contains("akaza"));
            assertTrue(friendsS.contains("toshino"));
            assertTrue(friendsS.contains("yui"));

            assertEquals(525600, result.getInt("funds"));
            assertEquals(10, result.getInt("size"));

            assertTrue(result.getBoolean("protect"));
            assertFalse(result.getBoolean("privacy"));

        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void test_dejsonifyPlot() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", "toad");
            json.put("owner", "akarin");

            JSONArray coowners = new JSONArray();
            coowners.put("yuppi");
            coowners.put("kyoppi");
            coowners.put("sugiura");
            json.put("coowners", coowners);

            JSONArray friends = new JSONArray();
            friends.put("ayano");
            friends.put("blah");
            friends.put("misc");
            json.put("friends", friends);

            json.put("funds", 90210);
            json.put("size", 9001);

            JSONObject center = new JSONObject();
            center.put("world", "sekai");
            center.put("x", 442);
            center.put("y", -901);
            center.put("z", 685);
            json.put("center", center);

            json.put("protect", false);
            json.put("privacy", true);

            // Now the test
            Plot plot = instance.dejsonifyPlot(json);

            assertEquals("toad", plot.getName());
            assertEquals("akarin", plot.getOwner());

            assertTrue(plot.isCoowner("yuppi"));
            assertTrue(plot.isCoowner("kyoppi"));
            assertTrue(plot.isCoowner("sugiura"));

            assertTrue(plot.isFriend("ayano"));
            assertTrue(plot.isFriend("blah"));
            assertTrue(plot.isFriend("misc"));

            assertEquals(90210, plot.getFunds());
            assertEquals(9001, plot.getSize());

//            World sekai = mock(World.class);
//            when(server.getWorld(Mockito.eq("sekai"))).thenReturn(sekai);
            Location centerl = plot.getCenter();
//            assertEquals(sekai, centerl.getWorld());
            assertEquals(442, centerl.getBlockX());
            assertEquals(-901, centerl.getBlockY());
            assertEquals(685, centerl.getBlockZ());

            assertFalse(plot.isProtected());
            assertTrue(plot.isPrivate());
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
