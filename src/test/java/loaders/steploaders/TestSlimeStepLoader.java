package loaders.steploaders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import xenocraft.magicparkour.ParkourProperties;
import xenocraft.magicparkour.loaders.steploaders.SlimeStepLoader;
import xenocraft.magicparkour.steps.SlimeStep;

public class TestSlimeStepLoader {

    @Test
    public void testLoadStep() {
        JsonObject object = JsonParser.parseString("""
                {
                    "type": "slime",
                    "pos": [1, 23, 4]
                }
                """).getAsJsonObject();
        World worldMock = mock(World.class);

        ParkourProperties properties = new ParkourProperties(worldMock, Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK);

        SlimeStep expected = new SlimeStep(new Location(worldMock, 1, 23, 4), 1, 1);

        try {
            assertEquals(expected, SlimeStepLoader.loadStep(object, properties));
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

        ParkourProperties properties = new ParkourProperties(worldMock, Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK);

        SlimeStep expected = new SlimeStep(new Location(worldMock, 1, 23, 4), 3, 2);

        try {
            assertEquals(expected, SlimeStepLoader.loadStep(object, properties));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }
}
