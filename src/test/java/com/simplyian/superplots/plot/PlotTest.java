package com.simplyian.superplots.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlotTest {
    private Plot plot;

    @Before
    public void setUp() {
        World world = mock(World.class);
        Location center = new Location(world, 3, 4, 5);
        plot = new Plot("My Plot", "albireox", 10, center);
    }

    @After
    public void tearDown() {
        plot = null;
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
    public void test_distance() {
        Location me = new Location(plot.getCenter().getWorld(), 0, 0, 5);

        double expected = 5.0;
        double result = plot.distance(me);

        assertEquals(expected, result, 0.1);
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
        
    }
}
