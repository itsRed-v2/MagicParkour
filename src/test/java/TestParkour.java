import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import xenocraft.magicparkour.Parkour;
import xenocraft.magicparkour.ParkourProperties;
import xenocraft.magicparkour.steps.CheckPointStep;
import xenocraft.magicparkour.steps.SimpleStep;
import xenocraft.magicparkour.steps.StartStep;
import xenocraft.magicparkour.steps.Step;

public class TestParkour {

    @Test
    public void testCheckNumbering() {
        List<Step> steps = new ArrayList<>();
        steps.add(new CheckPointStep(mock(Location.class), Material.STONE));
        steps.add(mock(SimpleStep.class));
        steps.add(new CheckPointStep(mock(Location.class), Material.STONE));
        steps.add(new CheckPointStep(mock(Location.class), Material.STONE));
        steps.add(mock(SimpleStep.class));
        steps.add(mock(SimpleStep.class));
        steps.add(new CheckPointStep(mock(Location.class), Material.STONE));

        World mockWorld = mock(World.class);
        Location start = new Location(mockWorld, 1.5, 1.1 ,1.9);

        ParkourProperties properties = new ParkourProperties(mockWorld, Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK);
        Parkour parkour = new Parkour("parkourName", start, steps, properties);
        List<Step> resultSteps = parkour.steps();

        assertEquals(new StartStep(start, Material.DIAMOND_BLOCK), resultSteps.get(0));

        assertEquals(1, ((CheckPointStep) resultSteps.get(1)).checkIndex);
        assertEquals(3, ((CheckPointStep) resultSteps.get(3)).checkIndex);
        assertEquals(4, ((CheckPointStep) resultSteps.get(4)).checkIndex);
        assertEquals(7, ((CheckPointStep) resultSteps.get(7)).checkIndex);
    }
}
