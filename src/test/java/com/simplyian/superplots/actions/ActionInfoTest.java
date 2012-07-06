package com.simplyian.superplots.actions;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Sets;
import com.simplyian.superplots.EconHook;
import com.simplyian.superplots.MsgColor;
import com.simplyian.superplots.SPSettings;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class ActionInfoTest {
    private SuperPlotsPlugin main;
    private ActionInfo action;
    private PlotManager plotManager;
    private Player player;
    private EconHook econ;

    @Before
    public void setup() {
        main = mock(SuperPlotsPlugin.class);
        action = new ActionInfo(main);

        econ = mock(EconHook.class);
        when(main.getEconomy()).thenReturn(econ);

        plotManager = mock(PlotManager.class);
        when(main.getPlotManager()).thenReturn(plotManager);

        SPSettings settings = mock(SPSettings.class);
        when(main.getSettings()).thenReturn(settings);
        when(settings.getInfluenceMultiplier()).thenReturn(1.5);
        when(settings.getInitialPlotSize()).thenReturn(10);
        when(settings.getMinimumPlotSize()).thenReturn(5);

        Player bluejay = mock(Player.class);
        when(bluejay.getName()).thenReturn("BlueJayWay");
        mockStatic(Bukkit.class);
        when(Bukkit.getPlayer("bluejay")).thenReturn(bluejay);

        player = mock(Player.class);
        when(player.getName()).thenReturn("albireox");
    }

    @After
    public void tearDown() {
        main = null;
        action = null;
        plotManager = null;
        player = null;
    }

    @Test
    public void test_perform_notInPlot() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(null);

        List<String> args = Arrays.asList();
        action.perform(player, args);

        verify(player).sendMessage(contains("not in a plot"));
    }

    @Test
    public void test_success_outsider() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(player.getLocation()).thenReturn(playerLoc);
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(plot);
        when(plot.isOwner("albireox")).thenReturn(false);
        when(player.getName()).thenReturn("albireox");

        when(plot.getName()).thenReturn("My Plot");

        when(plot.getSize()).thenReturn(20);
        when(plot.getOwner()).thenReturn("albireox");
        when(plot.isProtected()).thenReturn(true);
        when(plot.isPrivate()).thenReturn(true);

        when(plot.dailyTax()).thenReturn(100);
        when(plot.getFunds()).thenReturn(223);
        when(plot.taxDaysLeft()).thenReturn(2);
        when(plot.distance(playerLoc)).thenReturn(12323.50009); // never will
                                                                // happen but oh
                                                                // well

        List<String> args = Arrays.asList();
        action.perform(player, args);

        verify(player).getLocation();
        verify(player).getName();

        verify(player).sendMessage(
                contains("Info for " + MsgColor.INFO_HILIGHT + "My Plot"));
        verify(player).sendMessage(
                and(and(contains("Size: "), contains("20")),
                        and(contains("Owner: "), contains("albireox"))));
        verify(player).sendMessage(
                and(and(contains("Protected: "), contains("yes")),
                        and(contains("Privacy: "), contains("private"))));

        Mockito.verifyNoMoreInteractions(player);
    }

    @Test
    public void test_success_admin() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(player.getLocation()).thenReturn(playerLoc);
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(plot);
        when(plot.isAdministrator("albireox")).thenReturn(true);
        when(player.getName()).thenReturn("albireox");

        when(plot.getName()).thenReturn("My Plot");

        when(plot.getSize()).thenReturn(20);
        when(plot.getOwner()).thenReturn("albireox");
        when(plot.isProtected()).thenReturn(true);
        when(plot.isPrivate()).thenReturn(true);

        when(plot.dailyTax()).thenReturn(100);
        when(plot.getFunds()).thenReturn(223);
        when(plot.taxDaysLeft()).thenReturn(2);
        when(plot.distance(playerLoc)).thenReturn(12323.50009); // never will
                                                                // happen but oh
                                                                // well
        when(plot.distancef(playerLoc)).thenReturn("12323.51");
        when(plot.getValue()).thenReturn(12312);

        Set<String> coowners = Sets.newHashSet("bob", "frank");
        Set<String> friends = Sets.newHashSet("marley", "joe", "kenny");

        when(plot.getCoowners()).thenReturn(coowners);
        when(plot.getFriends()).thenReturn(friends);

        List<String> args = Arrays.asList();
        action.perform(player, args);

        verify(player, times(2)).getLocation();
        verify(player).getName();

        verify(player).sendMessage(
                contains("Info for " + MsgColor.INFO_HILIGHT + "My Plot"));
        verify(player).sendMessage(
                and(and(contains("Size: "), contains("20")),
                        and(contains("Owner: "), contains("albireox"))));
        verify(player).sendMessage(
                and(and(contains("Protected: "), contains("yes")),
                        and(contains("Privacy: "), contains("private"))));

        // admin now
        verify(player).sendMessage(contains("Admin Info"));
        verify(player).sendMessage(
                and(and(contains("Daily Tax: "), contains("100")),
                        and(contains("Funds: "), contains("D223"))));

        verify(player).sendMessage(
                and(contains("Days of tax left: "), contains("2")));

        verify(player).sendMessage(
                and(contains("Distance from center: "),
                        contains("12323.51 blocks")));

        verify(player).sendMessage(
                and(contains("Plot value: "), contains("D12312")));

        verify(player).sendMessage(
                and(contains("Coowners: "),
                        and(contains("bob"), contains("frank"))));

        verify(player).sendMessage(
                and(contains("Friends: "),
                        and(contains("marley"),
                                and(contains("joe"), contains("kenny")))));

        Mockito.verifyNoMoreInteractions(player);
    }
}
