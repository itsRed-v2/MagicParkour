import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.steps.CheckPointStep;
import xenocraft.magicparkour.elements.steps.SimpleStep;

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

        Parkour parkour = new Parkour("parkourName", elements);
        List<ParkourElement> resultElements = parkour.elements();

        assertEquals(0, ((CheckPointStep) resultElements.get(0)).checkIndex);
        assertEquals(2, ((CheckPointStep) resultElements.get(2)).checkIndex);
        assertEquals(3, ((CheckPointStep) resultElements.get(3)).checkIndex);
        assertEquals(6, ((CheckPointStep) resultElements.get(6)).checkIndex);
    }
}
