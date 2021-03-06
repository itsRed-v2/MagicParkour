package xenocraft.magicparkour.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class JsonUtils {

    public static Vector getVector(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        InvalidConfigurationException exception = new InvalidConfigurationException(
                "element \"" + memberName + "\" must be a list of 3 numbers representing coordinates");

        if (!element.isJsonArray()) throw exception;
        JsonArray array = element.getAsJsonArray();

        if (array.size() != 3) throw exception;

        double[] values = new double[3];

        for (int i = 0; i < 3; i++) {
            JsonElement number = array.get(i);

            if (!number.isJsonPrimitive()) throw exception;

            try {
                values[i] = number.getAsDouble();
            } catch (NumberFormatException e) {
                throw exception;
            }
        }

        return new Vector(values[0], values[1], values[2]);
    }

    public static String getString(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);
        if (!element.isJsonPrimitive()) throw new InvalidConfigurationException("element \"" + memberName + "\" must be a string");
        return element.getAsString();
    }

    public static String getString(JsonObject object, String memberName, String defaultValue) throws InvalidConfigurationException {
        if (!object.has(memberName)) return defaultValue;
        return getString(object, memberName);
    }

    public static @NotNull BlockData getBlockData(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        if (!element.isJsonPrimitive()) throw new InvalidConfigurationException("element \"" + memberName + "\" must be a string corresponding to a block");
        String stringData = element.getAsString();

        BlockData block;
        try {
             block = Bukkit.createBlockData(stringData);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            throw new InvalidConfigurationException("element \"" + memberName + "\" must be a valid BlockData. e.g: \"minecraft:oak_slab[type=top]\"");
        }

        return block;
    }

    public static @NotNull BlockData getBlockData(JsonObject object, String memberName, BlockData defaultValue) throws InvalidConfigurationException {
        if (!object.has(memberName)) return defaultValue;
        return getBlockData(object, memberName);
    }

    public static int getInt(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        final InvalidConfigurationException exception = new InvalidConfigurationException("element \"" + memberName + "\" is not a valid integer");
        if (!element.isJsonPrimitive()) throw exception;

        int i;
        try {
            i = element.getAsInt();
        } catch (NumberFormatException | ClassCastException e) {
            throw exception;
        }
        return i;
    }

    public static int getInt(JsonObject object, String memberName, int defaultValue) throws InvalidConfigurationException {
        if (!object.has(memberName)) return defaultValue;
        return getInt(object, memberName);
    }

    public static boolean getBool(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        final InvalidConfigurationException exception = new InvalidConfigurationException("element \"" + memberName + "\" is not a valid boolean");
        if (!element.isJsonPrimitive()) throw exception;

        boolean bool;
        try {
            bool = element.getAsBoolean();
        } catch (ClassCastException e) {
            throw exception;
        }
        return bool;
    }

    public static boolean getBool(JsonObject object, String memberName, boolean defaultValue) throws InvalidConfigurationException {
        if (!object.has(memberName)) return defaultValue;
        return getBool(object, memberName);
    }

    public static JsonObject getObject(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        if (!element.isJsonObject()) throw new InvalidConfigurationException("element \"" + memberName + "\" must be a JSON object");
        return element.getAsJsonObject();
    }

    public static JsonArray getArray(JsonObject object, String memberName) throws InvalidConfigurationException {
        JsonElement element = getElement(object, memberName);

        if (!element.isJsonArray()) throw new InvalidConfigurationException("element \"" + memberName + "\" must be a JSON array");
        return element.getAsJsonArray();
    }

    public static JsonObject getArrayObject(JsonArray array, int index) throws InvalidConfigurationException {
        JsonElement element = array.get(index);

        if (!element.isJsonObject()) throw new InvalidConfigurationException("element at index " + index + " of Array must be a JSON object");
        return element.getAsJsonObject();
    }

    public static JsonElement getElement(JsonObject object , String memberName) throws InvalidConfigurationException {
        if (!object.has(memberName)) throw new InvalidConfigurationException("missing element \"" + memberName + "\"");
        return object.get(memberName);
    }

}
