package utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
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
                "blockdata1": "WHITE_STAINED_GLASS",
                "blockdata2": "stone_bricks",
                "complexBlockdata": "ladder[facing=west]",
                "invalidMaterial": "stone-bricks",
                "nestedObject": {"foo": "bar"}
            }
            """).getAsJsonObject();

    private static final JsonArray array = JsonParser.parseString("""
            [
                { "number": 5 },
                { "number": 7},
                10
            ]
            """).getAsJsonArray();

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
    
//    public void testGetBlockData() {
//        try {
//            assertEquals(Bukkit.createBlockData("minecraft:white_stained_glass"), JsonUtils.getBlockData(object, "blockdata1"));
//            assertEquals(Bukkit.createBlockData("minecraft:stone_bricks"), JsonUtils.getBlockData(object, "blockdata2"));
//            assertEquals(Bukkit.createBlockData("minecraft:ladder[facing=west]"), JsonUtils.getBlockData(object, "complexBlockdata"));
//        } catch (InvalidConfigurationException e) {
//            fail("no exception expected but got: " + e.getMessage());
//        }
//    }
//
//    public void testInvalidGetBlockData() {
//        expectError(() -> JsonUtils.getBlockData(object, "invalidMaterial"),
//                "\"stone-bricks\" does not correspond to a material");
//        expectError(() -> JsonUtils.getBlockData(object, "number"),
//                "\"7.92\" does not correspond to a material");
//
//        expectError(() -> JsonUtils.getBlockData(object, "vector"),
//                "element \"vector\" must be a string corresponding to a material");
//    }
//
//    public void testDefaultGetBlockData() {
//        try {
//            assertEquals(Bukkit.createBlockData(Material.REDSTONE_BLOCK), JsonUtils.getBlockData(object, "inextisting-field", Bukkit.createBlockData(Material.REDSTONE_BLOCK)));
//            assertEquals(Bukkit.createBlockData(Material.STONE_BRICKS), JsonUtils.getBlockData(object, "material2", Bukkit.createBlockData(Material.REDSTONE_BLOCK)));
//        } catch (InvalidConfigurationException e) {
//            fail("no exception expected but got: " + e.getMessage());
//        }
//
//        expectError(() -> JsonUtils.getBlockData(object, "number", Bukkit.createBlockData(Material.REDSTONE_BLOCK)),
//                "\"7.92\" does not correspond to a block");
//        expectError(() -> JsonUtils.getBlockData(object, "vector", Bukkit.createBlockData(Material.REDSTONE_BLOCK)),
//                "element \"vector\" must be a string corresponding to a block");
//    }


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

    public void testGetArrayObject() {
        try {
            JsonElement elem1 = JsonParser.parseString("{ \"number\": 5 }");
            JsonElement elem2 = JsonParser.parseString("{ \"number\": 7 }");

            assertEquals(elem1, JsonUtils.getArrayObject(array, 0));
            assertEquals(elem2, JsonUtils.getArrayObject(array, 1));
        } catch (InvalidConfigurationException e) {
            fail("no exception expected but got: " + e.getMessage());
        }
    }

    public void testInvalidGetArrayObject() {
        expectError(() -> JsonUtils.getArrayObject(array, 2),
                "element at index 2 of Array must be a JSON object");
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
