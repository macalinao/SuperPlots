package com.simplyian.superplots.actions;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.simplyian.superplots.EconHook;
import com.simplyian.superplots.SPSettings;
import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotManager;

public class ActionCreateTest {
    private SuperPlotsPlugin main;
    private ActionCreate action;
    private EconHook econ;
    private PlotManager plotManager;
    private Player player;

    @Before
    public void setup() {
        main = mock(SuperPlotsPlugin.class);
        action = new ActionCreate(main);

        econ = mock(EconHook.class);
        when(main.getEconomy()).thenReturn(econ);

        plotManager = mock(PlotManager.class);
        when(main.getPlotManager()).thenReturn(plotManager);

        SPSettings settings = mock(SPSettings.class);
        when(main.getSettings()).thenReturn(settings);
        when(settings.getInfluenceMultiplier()).thenReturn(1.5);
        when(settings.getInitialPlotSize()).thenReturn(10);
        when(settings.getBaseDiamonds()).thenReturn(1);

        player = mock(Player.class);
        when(player.getName()).thenReturn("albireox");
    }

    @After
    public void tearDown() {
        main = null;
        action = null;
    }

    @Test
    public void test_perform_tooClose() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                9.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);

        List<String> args = Arrays.asList("Test");
        action.perform(player, args);
        verify(player).sendMessage(contains("too close"));
    }

    @Test
    public void test_perform_nameTaken() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("test")).thenReturn(closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);

        List<String> args = Arrays.asList("test");
        action.perform(player, args);
        verify(player).sendMessage(contains("already taken"));
    }

    @Test
    public void test_perform_noName() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("test")).thenReturn(closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);

        List<String> args = Arrays.asList();
        action.perform(player, args);
        verify(player).sendMessage(contains("must enter a name"));
    }

    @Test
    public void test_perform_invalidName() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("test")).thenReturn(closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);

        List<String> args = Arrays.asList("This", "is", "my", "plot%");
        action.perform(player, args);
        verify(player).sendMessage(contains("is invalid"));
    }

    @Test
    public void test_perform_noCash() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("asdf")).thenReturn(closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);
        when(econ.getBalance(player.getName())).thenReturn(100.0);

        PlayerInventory inv = mock(PlayerInventory.class);
        when(player.getInventory()).thenReturn(inv);
        ItemStack[] contents = new ItemStack[] { new ItemStack(Material.DIRT, 1) };
        when(inv.getContents()).thenReturn(contents);

        List<String> args = Arrays.asList("test", "plot!");
        action.perform(player, args);
        verify(player).sendMessage(contains("enough money"));
    }

    @Test
    public void test_perform_noDiamond() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("asdf")).thenReturn(closePlot);

        Location loc = new Location(null, 0, 0, 0);
        when(player.getLocation()).thenReturn(loc);
        when(econ.getBalance(player.getName())).thenReturn(1000.0);

        PlayerInventory inv = mock(PlayerInventory.class);
        when(player.getInventory()).thenReturn(inv);
        ItemStack[] contents = new ItemStack[] { new ItemStack(Material.DIRT, 1) };
        when(inv.getContents()).thenReturn(contents);

        List<String> args = Arrays.asList("test", "plot!");
        action.perform(player, args);
        verify(player).sendMessage(contains("enough diamond"));
    }

    @Test
    public void test_perform_success() {
        Plot closePlot = mock(Plot.class);
        when(closePlot.influenceEdgeDistance(any(Location.class))).thenReturn(
                20.0);

        when(plotManager.getClosestPlotAt(any(Location.class))).thenReturn(
                closePlot);
        when(plotManager.getPlotByName("asdf")).thenReturn(closePlot);

        Location loc = mock(Location.class);
        when(player.getLocation()).thenReturn(loc);
        Block block = mock(Block.class);
        when(loc.getBlock()).thenReturn(block);
        when(block.getLocation()).thenReturn(loc);
        when(econ.getBalance(player.getName())).thenReturn(1000.0);

        PlayerInventory inv = mock(PlayerInventory.class);
        when(player.getInventory()).thenReturn(inv);
        ItemStack[] contents = new ItemStack[] { new ItemStack(
                Material.DIAMOND, 1) };
        when(inv.getContents()).thenReturn(contents);

        List<String> args = Arrays.asList("test", "plot!");
        action.perform(player, args);
        verify(player).sendMessage(contains("created"));
        verify(plotManager).createPlot("test plot!", "albireox", 10,
                player.getLocation());
    }
}
