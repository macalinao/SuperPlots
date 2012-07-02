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
}
