package com.simplyian.superplots.listeners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.simplyian.superplots.SuperPlotsPlugin;
import com.simplyian.superplots.event.SPEventFactory;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotManager;

public class MainListenerTest {
    private SuperPlotsPlugin main;
    private MainListener instance;
    private PlotManager plotManager;
    private SPEventFactory eventFactory;
    
    @Before
    public void setup() {
        main = mock(SuperPlotsPlugin.class);
        eventFactory = mock(SPEventFactory.class);
        when(main.getEventFactory()).thenReturn(eventFactory);
        
        plotManager = mock(PlotManager.class);
        when(main.getPlotManager()).thenReturn(plotManager);
        
        instance = new MainListener(main);
    }

    @Test
    public void test_onPlayerMove_wilderness() {
        Player bob = mock(Player.class);
        when(bob.getName()).thenReturn("bob");
        
        World world = mock(World.class);
        Location from = mock(Location.class);
        Location to = mock(Location.class);
        
        Block playerBlock = mock(Block.class);
        when(bob.getLocation()).thenReturn(to);
        when(playerBlock.getLocation()).thenReturn(to);
        when(to.getBlock()).thenReturn(playerBlock);
        
        PlayerMoveEvent event = new PlayerMoveEvent(bob, from, to);
        
        instance.pplots.put("bob", null);
        instance.onPlayerMove(event);
        
        Mockito.verifyZeroInteractions(eventFactory);
    }

    @Test
    public void test_onPlayerMove_intoPlot() {
        Player bob = mock(Player.class);
        when(bob.getName()).thenReturn("bob");
        
        World world = mock(World.class);
        Location from = mock(Location.class);
        Location to = mock(Location.class);
        
        Block playerBlock = mock(Block.class);
        when(bob.getLocation()).thenReturn(to);
        when(playerBlock.getLocation()).thenReturn(to);
        when(to.getBlock()).thenReturn(playerBlock);
        
        PlayerMoveEvent event = new PlayerMoveEvent(bob, from, to);
        
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(to)).thenReturn(plot);
        
        instance.pplots.put("bob", null);
        instance.onPlayerMove(event);
        
        verify(eventFactory).callPlotEnterEvent(bob, plot);
        
        Plot result = instance.pplots.get("bob");
        assertEquals(plot, result);
        
        Mockito.verifyNoMoreInteractions(eventFactory);
    }

    @Test
    public void test_onPlayerMove_outOfPlot() {
        Player bob = mock(Player.class);
        when(bob.getName()).thenReturn("bob");
        
        World world = mock(World.class);
        Location from = mock(Location.class);
        Location to = mock(Location.class);
        
        Block playerBlock = mock(Block.class);
        when(bob.getLocation()).thenReturn(to);
        when(playerBlock.getLocation()).thenReturn(to);
        when(to.getBlock()).thenReturn(playerBlock);
        
        PlayerMoveEvent event = new PlayerMoveEvent(bob, from, to);
        
        Plot plot = mock(Plot.class);
        when(plotManager.getPlotAt(to)).thenReturn(null);
        
        instance.pplots.put("bob", plot);
        instance.onPlayerMove(event);
        
        verify(eventFactory).callPlotExitEvent(bob, plot);
        
        Plot result = instance.pplots.get("bob");
        assertNull(result);
        
        Mockito.verifyNoMoreInteractions(eventFactory);
    }
}
