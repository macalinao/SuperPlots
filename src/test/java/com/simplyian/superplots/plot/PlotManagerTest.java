package com.simplyian.superplots.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.event.SPEventFactory;

public class PlotManagerTest {
    private PlotManager pm;

    @Before
    public void setup() {
        SuperPlots main = mock(SuperPlots.class);
        pm = new PlotManager(main);

        SPEventFactory eventFactory = mock(SPEventFactory.class);
        when(main.getEventFactory()).thenReturn(eventFactory);
    }

    @After
    public void tearDown() {
        pm = null;
    }

    @Test
    public void test_getClosestPlotAt() {
        World world = mock(World.class);

        Location center1 = new Location(world, 0, 0, 0);
        Plot plot1 = pm.createPlot("My Plot", "albireox", 10, center1);

        Location center2 = new Location(world, 40, 0, 0);
        Plot plot2 = pm.createPlot("My Plot2", "albireox", 10, center2);

        Plot result = pm.getClosestPlotAt(new Location(world, 27, 0, 0));

        assertEquals(plot2, result);
    }

    @Test
    public void test_getPlotAt() {
        World world = mock(World.class);

        Location center1 = new Location(world, 0, 0, 0);
        Plot plot1 = pm.createPlot("My Plot", "albireox", 10, center1);

        Location center2 = new Location(world, 10, 10, 10);
        Plot plot2 = pm.createPlot("My Plot", "albireox", 10, center2);

        Plot result = pm.getPlotAt(new Location(world, 9, 9, 9));

        assertEquals(plot2, result);
    }

    @Test
    public void test_createPlot() {
        World world = mock(World.class);
        Location center = new Location(world, 0, 0, 0);
        Plot result = pm.createPlot("My Plot", "albireox", 10, center);

        assertEquals("My Plot", result.getName());
        assertEquals("albireox", result.getOwner());
        assertEquals(10, result.getSize());
        assertEquals(center, result.getCenter());
    }

    @Test
    public void test_destroyPlot() {
        World world = mock(World.class);
        Location center = new Location(world, 0, 0, 0);
        Plot result = pm.createPlot("My Plot", "albireox", 10, center);

        assertTrue(pm.getPlots().contains(result));
        assertTrue(pm.destroyPlot(result));

        assertFalse(pm.getPlots().contains(result));
        assertFalse(pm.destroyPlot(result));
    }

    @Test
    public void test_getPlotByName() {
        World world = mock(World.class);
        Location center = new Location(world, 0, 0, 0);
        Plot expected = pm.createPlot("My Plot", "albireox", 10, center);
        Plot result = pm.getPlotByName("My Plot");
        
        assertEquals(expected, result);
    }

    @Test
    public void test_getPlotByName_i() {
        World world = mock(World.class);
        Location center = new Location(world, 0, 0, 0);
        Plot expected = pm.createPlot("My Plot", "albireox", 10, center);
        Plot result = pm.getPlotByName("mY PLot");
        
        assertEquals(expected, result);
    }
}
