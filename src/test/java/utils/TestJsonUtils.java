package utils;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import xenocraft.magicparkour.utils.JsonUtils;

public class TestJsonUtils extends TestCase {

    private static final JsonObject object = JsonParser.parseString("""
            {
                "boolean": true,
                "string": "hello",
                "number": 7.92,
                "int": 573,
                "double": 2.925;
                "vector": [10, .1, 1.62],
                "vector2": ["6.2", 1051262562, 7],
                "4lengthVector": [4, 62, 13, 1],
                "2lengthVector": [4, 62],
                "wrongVector": [230.67, 2, "abc"],
                "stringArray": ["foo", "bar"],
                "material1": "WHITE_STAINED_GLASS",
                "material2": "stone_bricks",
                "invalidMaterial": "stone-bricks",
                "nestedObject": {"foo": "bar"}
            }
            """).getAsJsonObject();

    private interface ExceptionRunnable {
        void run() throws InvalidConfigurationException;
    }

    private void expectError(ExceptionRunnable runnable, String expectedErrorMessage) {
        try {
            runnable.run();
            fail("Expected exception with message: " + expectedErrorMessage);
        } catch (InvalidConfigurationException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    public void testGetVector() {
        try {
            assertEquals(new Vector(10, .1, 1.62), JsonUtils.getVector(object, "vector"));
            assertEquals(new Vector(6.2, 1051262562, 7), JsonUtils.getVector(object, "vector2"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetVector() {
        expectError(() -> JsonUtils.getVector(object, "4lengthVector"),
                "element \"4lengthVector\" must be a list of 3 numbers representing coordinates");
        expectError(() -> JsonUtils.getVector(object, "2lengthVector"),
                "element \"2lengthVector\" must be a list of 3 numbers representing coordinates");
        expectError(() -> JsonUtils.getVector(object, "wrongVector"),
                "element \"wrongVector\" must be a list of 3 numbers representing coordinates");
        expectError(() -> JsonUtils.getVector(object, "nestedObject"),
                "element \"nestedObject\" must be a list of 3 numbers representing coordinates");
        expectError(() -> JsonUtils.getVector(object, "number"),
                "element \"number\" must be a list of 3 numbers representing coordinates");
    }

    public void testGetString() {
        try {
            assertEquals("hello", JsonUtils.getString(object, "string"));
            assertEquals("7.92", JsonUtils.getString(object, "number"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetString() {
        expectError(() -> JsonUtils.getString(object, "vector"),
                "element \"vector\" must be a string");
        expectError(() -> JsonUtils.getString(object, "wrongVector"),
                "element \"wrongVector\" must be a string");
        expectError(() -> JsonUtils.getString(object, "stringArray"),
                "element \"stringArray\" must be a string");
        expectError(() -> JsonUtils.getString(object, "nestedObject"),
                "element \"nestedObject\" must be a string");
    }

    public void testDefaultGetString() {
        try {
            assertEquals("default", JsonUtils.getString(object, "inextisting-field", "default"));
            assertEquals("hello", JsonUtils.getString(object, "string", "default"));

        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }

        expectError(() -> JsonUtils.getString(object, "stringArray", "default"),
                "element \"stringArray\" must be a string");
        expectError(() -> JsonUtils.getString(object, "nestedObject", "default"),
                "element \"nestedObject\" must be a string");
    }
    
    public void testGetMaterial() {
        try {
            assertEquals(Material.WHITE_STAINED_GLASS, JsonUtils.getMaterial(object, "material1"));
            assertEquals(Material.STONE_BRICKS, JsonUtils.getMaterial(object, "material2"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetMaterial() {
        expectError(() -> JsonUtils.getMaterial(object, "invalidMaterial"),
                "\"stone-bricks\" does not correspond to a material");
        expectError(() -> JsonUtils.getMaterial(object, "number"),
                "\"7.92\" does not correspond to a material");

        expectError(() -> JsonUtils.getMaterial(object, "vector"),
                "element \"vector\" must be a string corresponding to a material");
    }

    public void testDefaultGetMaterial() {
        try {
            assertEquals(Material.REDSTONE_BLOCK, JsonUtils.getMaterial(object, "inextisting-field", Material.REDSTONE_BLOCK));
            assertEquals(Material.STONE_BRICKS, JsonUtils.getMaterial(object, "material2", Material.REDSTONE_BLOCK));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }

        expectError(() -> JsonUtils.getMaterial(object, "number", Material.REDSTONE_BLOCK),
                "\"7.92\" does not correspond to a material");
        expectError(() -> JsonUtils.getMaterial(object, "vector", Material.REDSTONE_BLOCK),
                "element \"vector\" must be a string corresponding to a material");
    }


    public void testGetInt() {
        try {
            assertEquals(573, JsonUtils.getInt(object, "int"));
            assertEquals(2, JsonUtils.getInt(object, "double"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetInt() {
        expectError(() -> JsonUtils.getInt(object, "string"),
                "element \"string\" is not a valid integer");
        expectError(() -> JsonUtils.getInt(object, "vector"),
                "element \"vector\" is not a valid integer");
        expectError(() -> JsonUtils.getInt(object, "nestedObject"),
                "element \"nestedObject\" is not a valid integer");
        expectError(() -> JsonUtils.getInt(object, "boolean"),
                "element \"boolean\" is not a valid integer");
    }

    public void testDefaultGetInt() {
        try {
            assertEquals(5, JsonUtils.getInt(object, "inexistion-field", 5));
            assertEquals(2, JsonUtils.getInt(object, "double", 7));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }

        expectError(() -> JsonUtils.getInt(object, "boolean", 5),
                "element \"boolean\" is not a valid integer");
    }

    public void testGetArray() {
        try {
            JsonElement elem1 = JsonParser.parseString("[\"6.2\", 1051262562, 7]");
            JsonElement elem2 = JsonParser.parseString("[\"foo\", \"bar\"]");
            assertEquals(elem1, JsonUtils.getArray(object, "vector2"));
            assertEquals(elem2, JsonUtils.getArray(object, "stringArray"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetArray() {
        expectError(() -> JsonUtils.getArray(object, "string"),
                "element \"string\" must be a JSON array");
        expectError(() -> JsonUtils.getArray(object, "number"),
                "element \"number\" must be a JSON array");
        expectError(() -> JsonUtils.getArray(object, "boolean"),
                "element \"boolean\" must be a JSON array");
    }

    public void testGetElement() {
        JsonElement subElement1 = JsonParser.parseString("hello");
        JsonElement subElement2 = JsonParser.parseString("[\"6.2\", 1051262562, 7]");
        try {
            assertEquals(subElement1, JsonUtils.getElement(object, "string"));
            assertEquals(subElement2, JsonUtils.getElement(object, "vector2"));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetElement() {
        expectError(() -> JsonUtils.getElement(object, "hello"),
                "missing element \"hello\"");
    }
}
