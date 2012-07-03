package com.simplyian.superplots.action;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.simplyian.superplots.SPSettings;
import com.simplyian.superplots.SuperPlots;
import com.simplyian.superplots.actions.ActionCreate;
import com.simplyian.superplots.plot.Plot;
import com.simplyian.superplots.plot.PlotManager;

public class ActionCreateTest {
    private SuperPlots main;
    private ActionCreate action;
    private PlotManager plotManager;
    private Player player;

    @Before
    public void setup() {
        main = mock(SuperPlots.class);
        action = new ActionCreate(main);

        plotManager = mock(PlotManager.class);
        when(main.getPlotManager()).thenReturn(plotManager);

        SPSettings settings = mock(SPSettings.class);
        when(main.getSettings()).thenReturn(settings);
        when(settings.getInfluenceMultiplier()).thenReturn(1.5);
        when(settings.getInitialPlotSize()).thenReturn(10);

        player = mock(Player.class);
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
                14.0);

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
}
