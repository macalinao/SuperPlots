package com.simplyian.superplots.actions;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.simplyian.superplots.EconHook;
import com.simplyian.superplots.SPSettings;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotManager;

public class ActionPrivateTest {
    private SuperPlotsPlugin main;
    private ActionPrivate action;
    private PlotManager plotManager;
    private Player player;
    private EconHook econ;

    @Before
    public void setup() {
        main = mock(SuperPlotsPlugin.class);
        action = new ActionPrivate(main);

        econ = mock(EconHook.class);
        when(main.getEconomy()).thenReturn(econ);

        plotManager = mock(PlotManager.class);
        when(main.getPlotManager()).thenReturn(plotManager);

        SPSettings settings = mock(SPSettings.class);
        when(main.getSettings()).thenReturn(settings);
        when(settings.getInfluenceMultiplier()).thenReturn(1.5);
        when(settings.getInitialPlotSize()).thenReturn(10);

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

        List<String> args = Arrays.asList("asdf");
        action.perform(player, args);

        verify(player).sendMessage(contains("not in a plot"));
    }

    @Test
    public void test_perform_mustBeOwner() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(player.getLocation()).thenReturn(playerLoc);
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(plot);
        when(plot.isOwner("albireox")).thenReturn(false);
        when(player.getName()).thenReturn("albireox");

        List<String> args = Arrays.asList("asdf");
        action.perform(player, args);

        verify(player).sendMessage(contains("must be the owner"));
    }

    @Test
    public void test_perform_alreadyProtected() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(player.getLocation()).thenReturn(playerLoc);
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(plot);
        when(plot.isOwner("albireox")).thenReturn(true);
        when(plot.isPrivate()).thenReturn(true);
        when(player.getName()).thenReturn("albireox");

        List<String> args = Arrays.asList("asdf");
        action.perform(player, args);

        verify(player).sendMessage(contains("is already private"));
    }

    @Test
    public void test_perform_success() {
        World world = mock(World.class);
        Location playerLoc = new Location(world, 0, 0, 0);
        when(player.getLocation()).thenReturn(playerLoc);
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(playerLoc)).thenReturn(plot);
        when(plot.isOwner("albireox")).thenReturn(true);
        when(plot.isPrivate()).thenReturn(false);
        when(player.getName()).thenReturn("albireox");

        List<String> args = Arrays.asList("asdf");
        action.perform(player, args);

        verify(player).sendMessage(contains("has been made private"));
        verify(plot).setPrivate(true);
    }

}
