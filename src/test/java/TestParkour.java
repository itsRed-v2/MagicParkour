import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.steps.CheckPointStep;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.steps.SimpleStep;
import xenocraft.magicparkour.elements.steps.StartStep;

public class TestParkour {

    @Test
    public void testCheckNumbering() {
        List<ParkourElement> elements = new ArrayList<>();
        elements.add(new CheckPointStep(mock(Location.class), Material.STONE));
        elements.add(mock(SimpleStep.class));
        elements.add(new CheckPointStep(mock(Location.class), Material.STONE));
        elements.add(new CheckPointStep(mock(Location.class), Material.STONE));
        elements.add(mock(SimpleStep.class));
        elements.add(mock(SimpleStep.class));
        elements.add(new CheckPointStep(mock(Location.class), Material.STONE));

        World mockWorld = mock(World.class);
        Location start = new Location(mockWorld, 1.5, 1.1 ,1.9);

        ParkourProperties properties = new ParkourProperties(mockWorld, Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK);
        Parkour parkour = new Parkour("parkourName", start, elements, properties);
        List<ParkourElement> resultSteps = parkour.elements();

        assertEquals(new StartStep(start, Material.DIAMOND_BLOCK), resultSteps.get(0));

        assertEquals(1, ((CheckPointStep) resultSteps.get(1)).checkIndex);
        assertEquals(3, ((CheckPointStep) resultSteps.get(3)).checkIndex);
        assertEquals(4, ((CheckPointStep) resultSteps.get(4)).checkIndex);
        assertEquals(7, ((CheckPointStep) resultSteps.get(7)).checkIndex);
    }
}
