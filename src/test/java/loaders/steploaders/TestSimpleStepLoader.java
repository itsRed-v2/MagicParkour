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
import xenocraft.magicparkour.loaders.StepLoader;
import xenocraft.magicparkour.loaders.steploaders.SimpleStepLoader;
import xenocraft.magicparkour.steps.SimpleStep;

public class TestSimpleStepLoader {

    private void expectError(String jsonInput, String expectedErrorMessage) {
        JsonObject object = JsonParser.parseString(jsonInput).getAsJsonObject();
        ParkourProperties properties = new ParkourProperties(mock(World.class), Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.NETHERITE_BLOCK);
        try {
            StepLoader.loadStep(object, properties);
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
        World worldMock = mock(World.class);

        ParkourProperties properties = new ParkourProperties(worldMock, Material.WHITE_STAINED_GLASS, Material.GOLD_BLOCK, Material.NETHERITE_BLOCK);

        SimpleStep expected = new SimpleStep(new Location(worldMock, 1, 23, 4), Material.WHITE_STAINED_GLASS);
        
        try {
            assertEquals(expected, SimpleStepLoader.loadStep(object, properties));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }
}
