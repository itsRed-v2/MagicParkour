package loaders.steploaders;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.steps.SlimeStep;
import xenocraft.magicparkour.loaders.steps.SlimeStepLoader;

public class TestSlimeStepLoader {

    @Test
    public void testLoadStep() {
        JsonObject object = JsonParser.parseString("""
                {
                    "type": "slime",
                    "pos": [1, 23, 4]
                }
                """).getAsJsonObject();
        JsonObject object2 = JsonParser.parseString("""
                {
                    "type": "slime",
                    "pos": [1, 23, 4],
                    "scope": true
                }
                """).getAsJsonObject();
        
        World worldMock = mock(World.class);

        ParkourProperties properties = new ParkourProperties(worldMock, mock(BlockData.class), mock(BlockData.class));

        SlimeStep expected = new SlimeStep(new Location(worldMock, 1, 23, 4), 1, 1, false);
        SlimeStep expected2 = new SlimeStep(new Location(worldMock, 1, 23, 4), 1, 1, true);

        try {
            assertEquals(expected, SlimeStepLoader.load(object, properties));
            assertEquals(expected2, SlimeStepLoader.load(object2, properties));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    @Test
    public void testLoadSized() {
        JsonObject object = JsonParser.parseString("""
                {
                    "type": "slime",
                    "pos": [1, 23, 4],
                    "sizeX": 3,
                    "sizeZ": 2.5
                }
                """).getAsJsonObject();
        
        World worldMock = mock(World.class);

        ParkourProperties properties = new ParkourProperties(worldMock, mock(BlockData.class), mock(BlockData.class));

        SlimeStep expected = new SlimeStep(new Location(worldMock, 1, 23, 4), 3, 2, false);

        try {
            assertEquals(expected, SlimeStepLoader.load(object, properties));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }
}
