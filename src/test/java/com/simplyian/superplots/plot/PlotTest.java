package com.simplyian.superplots.plot;

import static org.junit.Assert.assertEquals;
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
        plot = new Plot();
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
    public void test_distanceSquared() {
        World world = mock(World.class);
        Location center = new Location(world, 3, 4, 5);
        plot.setCenter(center);
        Location me = new Location(world, 0, 0, 5);

        double expected = 5.0;
        double result = plot.distanceSquared(me);

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void test_edgeDistanceSquared() {
        World world = mock(World.class);
        Location center = new Location(world, 3, 4, 5);
        plot.setCenter(center);
        plot.setSize(1);
        Location me = new Location(world, 0, 0, 5);

        double expected = 4.0;
        double result = plot.edgeDistanceSquared(me);

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
}
