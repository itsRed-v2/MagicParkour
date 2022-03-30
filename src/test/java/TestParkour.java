import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.steps.CheckPointStep;
import xenocraft.magicparkour.elements.steps.SimpleStep;
import xenocraft.magicparkour.elements.steps.StartStep;

public class TestParkour {

    @Test
    public void testCheckNumbering() {
        List<ParkourElement> elements = new ArrayList<>();
        elements.add(new CheckPointStep(mock(Location.class), mock(BlockData.class)));
        elements.add(mock(SimpleStep.class));
        elements.add(new CheckPointStep(mock(Location.class), mock(BlockData.class)));
        elements.add(new CheckPointStep(mock(Location.class), mock(BlockData.class)));
        elements.add(mock(SimpleStep.class));
        elements.add(mock(SimpleStep.class));
        elements.add(new CheckPointStep(mock(Location.class), mock(BlockData.class)));

        World mockWorld = mock(World.class);
        Location start = new Location(mockWorld, 1.5, 1.1 ,1.9);

        BlockData startBlock = mock(BlockData.class);
        ParkourProperties properties = new ParkourProperties(mockWorld, mock(BlockData.class), mock(BlockData.class), startBlock);
        
        Parkour parkour = new Parkour("parkourName", start, elements, properties);
        List<ParkourElement> resultElements = parkour.elements();

        assertEquals(new StartStep(start, startBlock), resultElements.get(0));

        assertEquals(1, ((CheckPointStep) resultElements.get(1)).checkIndex);
        assertEquals(3, ((CheckPointStep) resultElements.get(3)).checkIndex);
        assertEquals(4, ((CheckPointStep) resultElements.get(4)).checkIndex);
        assertEquals(7, ((CheckPointStep) resultElements.get(7)).checkIndex);
    }
}
