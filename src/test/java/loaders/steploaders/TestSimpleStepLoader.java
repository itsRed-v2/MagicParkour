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
import xenocraft.magicparkour.elements.steps.SimpleStep;
import xenocraft.magicparkour.loaders.ElementLoader;
import xenocraft.magicparkour.loaders.steps.SimpleStepLoader;

public class TestSimpleStepLoader {

    private void expectError(String jsonInput, String expectedErrorMessage) {
        JsonObject object = JsonParser.parseString(jsonInput).getAsJsonObject();
        
        ParkourProperties properties = new ParkourProperties(mock(World.class), mock(BlockData.class), mock(BlockData.class));

        try {
            ElementLoader.load(object, properties);
            fail("Expected exception with message: " + expectedErrorMessage);
        } catch (InvalidConfigurationException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void testNoPos() {
        expectError("""
                {
                    "world": "worldName"
                }
                """,
                "missing element \"pos\"");
    }

    @Test
    public void testInvalidPos() {
        expectError("""
                {
                    "pos": [10, "foo", 2]
                }
                """,
                "element \"pos\" must be a list of 3 numbers representing coordinates");
    }

    @Test
    public void testLoadStep() {
        JsonObject object = JsonParser.parseString("""
                {
                    "pos": [1, 23, 4]
                }
                """).getAsJsonObject();
        JsonObject object2 = JsonParser.parseString("""
                {
                    "pos": [1, 23, 4],
                    "scope": false
                }
                """).getAsJsonObject();

        World worldMock = mock(World.class);

        BlockData baseBlock = mock(BlockData.class);
        ParkourProperties properties = new ParkourProperties(worldMock, baseBlock, mock(BlockData.class));

        SimpleStep expected = new SimpleStep(new Location(worldMock, 1, 23, 4), baseBlock, true);
        SimpleStep expected2 = new SimpleStep(new Location(worldMock, 1, 23, 4), baseBlock, false);
        
        try {
            assertEquals(expected, SimpleStepLoader.load(object, properties));
            assertEquals(expected2, SimpleStepLoader.load(object2, properties));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }
}
