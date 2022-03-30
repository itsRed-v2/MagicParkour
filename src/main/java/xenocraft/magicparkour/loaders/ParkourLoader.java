package xenocraft.magicparkour.loaders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.utils.JsonUtils;

public class ParkourLoader {

    public static Parkour loadParkour(JsonElement rootElement, String parkourID) throws InvalidConfigurationException {
        JsonObject rootObject = rootElement.getAsJsonObject();

        // Parsing potential custom name
        String parkourName = JsonUtils.getString(rootObject, "customName", parkourID);

        // Parsing world
        String worldName = JsonUtils.getString(rootObject, "world");
        World world = Bukkit.getWorld(worldName);
        assertNotNull(world, "provided world doesn't exist");

        // Parsing start data
        JsonObject startObject = JsonUtils.getObject(rootObject, "start").getAsJsonObject();

        Vector startVector = JsonUtils.getVector(startObject, "pos");
        Location start = new Location(world, startVector.getX(), startVector.getY(), startVector.getZ());

        BlockData startBlock = JsonUtils.getBlockData(startObject, "block", Bukkit.createBlockData(Material.GOLD_BLOCK));

        // Parsing properties
        BlockData baseBlock = JsonUtils.getBlockData(rootObject, "base_block", Bukkit.createBlockData(Material.WHITE_STAINED_GLASS));
        BlockData checkpointBlock = JsonUtils.getBlockData(rootObject, "checkpoint_block", Bukkit.createBlockData(Material.GOLD_BLOCK));

        ParkourProperties properties = new ParkourProperties(world, baseBlock, checkpointBlock, startBlock);

        // Parsing steps
        JsonElement stepArrayElement = JsonUtils.getElement(rootObject, "steps");
        assertNotNull(stepArrayElement, "no steps provided");
        if (!stepArrayElement.isJsonArray()) configError("element \"steps\" must be an array of steps");
        JsonArray jsonArray = stepArrayElement.getAsJsonArray();

        List<ParkourElement> steps = new ArrayList<>();

        for (JsonElement stepElement : jsonArray) {
            try {
                steps.add(ElementLoader.load(stepElement.getAsJsonObject(), properties));
            } catch (InvalidConfigurationException e) {
                configError("at step " + (steps.size() + 1) + ": " + e.getMessage());
            } catch (IllegalStateException ignored) {}
        }

        return new Parkour(parkourName, start, steps, properties);
    }

    @Contract("null,_ -> fail")
    private static void assertNotNull(Object object, String msg) throws InvalidConfigurationException {
        if (object == null) configError(msg);
    }

    @Contract("_ -> fail")
    private static void configError(String msg) throws InvalidConfigurationException {
        throw new InvalidConfigurationException(msg);
    }
}
