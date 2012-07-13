package com.simplyian.superplots.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.simplyian.superplots.SPSettings;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.SuperPlotsPlugin;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SuperPlots.class)
public class PlotTest {
    private SuperPlotsPlugin main;
    private PlotManager plotManager;
    private SPSettings settings;
    private Plot plot;

    @Before
    public void setUp() {
        mockStatic(SuperPlots.class);

        plotManager = mock(PlotManager.class);
        when(SuperPlots.getPlotManager()).thenReturn(plotManager);

        settings = mock(SPSettings.class);
        when(SuperPlots.getSettings()).thenReturn(settings);
        when(settings.getMinimumPlotSize()).thenReturn(5);
        when(settings.getInfluenceMultiplier()).thenReturn(1.5);
        when(settings.getTownInfluenceMultiplier()).thenReturn(2.0);

        World world = mock(World.class);
        Location center = new Location(world, 3, 4, 5);
        plot = new Plot("My Plot", "albireox", 10, center);
    }

    @After
    public void tearDown() {
        plot = null;
    }

    @Test
    public void test_kick() {
        plot.addCoowner("bob");
        assertTrue(plot.isCoowner("bob"));

        plot.kick("bob");
        assertFalse(plot.isCoowner("bob"));
        assertFalse(plot.isMember("bob"));
    }

    @Test
    public void test_kick_friend() {
        plot.addFriend("bob");
        assertTrue(plot.isFriend("bob"));

        plot.kick("bob");
        assertFalse(plot.isFriend("bob"));
        assertFalse(plot.isMember("bob"));
    }

    @Test
    public void test_kick_owner() {
        // Impossiburu!
        plot.setOwner("bob");
        assertTrue(plot.isOwner("bob"));

        plot.kick("bob");
        assertTrue(plot.isOwner("bob"));
        assertTrue(plot.isMember("bob"));
    }

    @Test
    public void test_addFunds() {
        plot.setFunds(10);
        plot.addFunds(130);

        int expected = 140;
        int result = plot.getFunds();

        assertEquals(expected, result);
    }

    @Test
    public void test_subtractFunds() {
        plot.setFunds(270);
        plot.subtractFunds(130);

        int expected = 140;
        int result = plot.getFunds();

        assertEquals(expected, result);
    }

    @Test
    public void test_dailyTax() {
        plot.setSize(11);

        int expected = 55;
        int result = plot.dailyTax();

        assertEquals(expected, result);
    }

    @Test
    public void test_taxDaysLeft() {
        plot.setSize(11);
        plot.setFunds(1000);

        int expected = 18;
        int result = plot.taxDaysLeft();

        assertEquals(expected, result);
    }

    @Test
    public void test_processTax_0() {
        plot.setFunds(450);
        plot.setSize(80);

        int expected = 0;
        int result = plot.processTax();

        assertEquals(expected, result);

        int funds = plot.getFunds();
        assertEquals(50, funds);
    }

    @Test
    public void test_processTax_1() {
        plot.setFunds(450);
        plot.setSize(100);

        int expected = 1;
        int result = plot.processTax();

        assertEquals(expected, result);

        int funds = plot.getFunds();
        assertEquals(450, funds);

        int size = plot.getSize();
        assertEquals(99, size);
    }

    @Test
    public void test_processTax_2() {
        plot.setFunds(20);
        plot.setSize(5);

        int expected = 2;
        int result = plot.processTax();

        assertEquals(expected, result);
        verify(plotManager).disbandPlot(plot);
    }

    @Test
    public void test_distance() {
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 5.0;
        double result = plot.distance(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_distancef() {
        Location me = new Location(plot.getCenter().getWorld(), 123, 456, 5);

        String expected = "467.66";
        String result = plot.distancef(me);

        assertEquals(expected, result);
    }

    @Test
    public void test_distanceSquared() {
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 25.0;
        double result = plot.distanceSquared(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_edgeDistance() {
        plot.setSize(1);
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 4.0;
        double result = plot.edgeDistance(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_influenceEdgeDistance() {
        plot.setSize(1);
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 3.5;
        double result = plot.influenceEdgeDistance(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_influenceEdgeDistance_town() {
        plot.setSize(1);
        plot.addUpgrade(PlotUpgrade.TOWN);
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 3.0;
        double result = plot.influenceEdgeDistance(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_expand() {
        plot.setSize(4);
        plot.expand();

        int expected = 5;
        int result = plot.getSize();

        assertEquals(expected, result);
    }

    @Test
    public void test_expandNum() {
        plot.setSize(4);
        plot.expand(3);

        int expected = 7;
        int result = plot.getSize();

        assertEquals(expected, result);
    }

    @Test
    public void test_shrink() {
        plot.setSize(15);
        plot.shrink(4);

        int expected = 11;
        int result = plot.getSize();

        assertEquals(expected, result);
    }

    @Test
    public void test_contains() {
        Location inside = new Location(plot.getCenter().getWorld(), 3, 6, 6);
        Location outside = new Location(plot.getCenter().getWorld(), 20, 6, 6);
        plot.setSize(10);

        assertTrue(plot.contains(inside));
        assertFalse(plot.contains(outside));
    }

    @Test
    public void test_getValue() {
        plot.setSize(10);
        plot.setFunds(10000); // We don't care

        int expected = 550;
        int result = plot.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void test_isValidName() {
        assertTrue(Plot.isValidName("albireox's house"));
        assertTrue(Plot.isValidName("albireox's house!"));
        assertTrue(Plot.isValidName("L0l this plot is AMAZING!!!!!111"));

        assertFalse(Plot
                .isValidName("This name is tooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo long."));
        assertFalse(Plot.isValidName("This#@$@$"));
        assertFalse(Plot.isValidName(""));
    }
}
