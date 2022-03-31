package loaders;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.loaders.ParkourLoader;

public class TestParkourLoader {

    private MockedStatic<Bukkit> mockBukkit;

    @Before
    public void setUp() {
        mockBukkit = Mockito.mockStatic(Bukkit.class);
        mockBukkit.when(() -> Bukkit.getWorld("worldName")).thenReturn(mock(World.class));
        mockBukkit.when(() -> Bukkit.getWorld("invalidWorldName")).thenReturn(null);
    }

    @After
    public void tearDown() {
        if (!mockBukkit.isClosed()) mockBukkit.close();
    }

    private void expectError(String jsonInput, String expectedErrorMessage) {
        JsonElement element = JsonParser.parseString(jsonInput);
        try {
            ParkourLoader.loadParkour(element, "parkourID");
            fail("Expected exception with message: " + expectedErrorMessage);
        } catch (InvalidConfigurationException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void testCustomName() {
        JsonElement element = JsonParser.parseString("""
                {
                    "world": "worldName",
                    "start": {
                        "pos": [10, 4, 2]
                    },
                    "customName": "§6Lobby parkour",
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """);

        try {
            Parkour parkour = ParkourLoader.loadParkour(element, "parkourID");
            assertEquals("§6Lobby parkour", parkour.name());
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    @Test
    public void testNoCustomName() {
        JsonElement element = JsonParser.parseString("""
                {
                    "world": "worldName",
                    "start": {
                        "pos": [10, 4, 2]
                    },
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """);

        try {
            Parkour parkour = ParkourLoader.loadParkour(element, "OtherParkourID");
            assertEquals("OtherParkourID", parkour.name());
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidCustomName() {
        expectError("""
                {
                    "world": "worldName",
                    "customName": [2, 5],
                    "start": {
                        "pos": [10, 4, 2]
                    },
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """,
                "element \"customName\" must be a string");
    }

    @Test
    public void testNoWorld() {
        expectError("""
                {
                    "start": {
                        "pos": [10, 4, 2]
                    },
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """,
                "missing element \"world\"");
    }

    @Test
    public void testInvalidWorld() {
        expectError("""
                {
                    "world": "invalidWorldName",
                    "start": {
                        "pos": [10, 4, 2]
                    },
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """,
                "world \"invalidWorldName\" doesn't exist");
    }

    @Test
    public void testNoStart() {
        expectError("""
                {
                    "world": "worldName",
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """,
                "missing element \"start\"");
    }
    
    @Test
    public void testInvalidStart() {
        expectError("""
                {
                    "world": "worldName",
                    "start": [10, 4, 2],
                    "steps": [],
                    "end": {
                        "pos": [12, 4, 2]
                    }
                }
                """,
                "element \"start\" must be a JSON object");
    }

//    @Test
//    public void testCatchStepLoaderError() {
//        JsonElement element = JsonParser.parseString("{\"foo3\":\"bar3\"}");
//
//        try (MockedStatic<StepLoader> mockStepLoader = Mockito.mockStatic(StepLoader.class)) {
//            mockStepLoader.when(() -> StepLoader.load(element, mock(World.class)))
//                    .thenThrow(new InvalidConfigurationException("exception message"));
//
//            expectError("""
//                {
//                    "world": "worldName",
//                    "start": [10, 4, 2],
//                    "steps": [
//                        { "foo": "bar" },
//                        { "foo2": "bar2" },
//                        { "foo3": "bar3" },
//                        { "foo4": "bar4" }
//                    ]
//                }
//                """,
//                "Error while loading parkour \"parkourID\": at step 3: exception message");
//        }
//    }
}
