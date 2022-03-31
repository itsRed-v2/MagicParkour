package xenocraft.magicparkour.loaders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.loaders.steps.EndStepLoader;
import xenocraft.magicparkour.loaders.steps.StartStepLoader;
import xenocraft.magicparkour.utils.JsonUtils;

public class ParkourLoader {

    public static Parkour loadParkour(JsonElement rootElement, String parkourID) throws InvalidConfigurationException {
        JsonObject rootObject = rootElement.getAsJsonObject();

        // Parsing potential custom name
        String parkourName = JsonUtils.getString(rootObject, "customName", parkourID);

        // Parsing world
        String worldName = JsonUtils.getString(rootObject, "world");
        World world = Bukkit.getWorld(worldName);
        assertNotNull(world, "world \"" + worldName + "\" doesn't exist");

        // Parsing properties
        BlockData baseBlock = JsonUtils.getBlockData(rootObject, "base_block", Bukkit.createBlockData(Material.WHITE_STAINED_GLASS));
        BlockData checkpointBlock = JsonUtils.getBlockData(rootObject, "checkpoint_block", Bukkit.createBlockData(Material.GOLD_BLOCK));

        ParkourProperties properties = new ParkourProperties(world, baseBlock, checkpointBlock);

        // Parsing steps
            List<ParkourElement> elements = new ArrayList<>();

            // Parsing start data
            JsonObject startObject = JsonUtils.getObject(rootObject, "start");
            elements.add(StartStepLoader.load(startObject, properties));

            // Parsing elements
            JsonArray jsonArray = JsonUtils.getArray(rootObject, "steps");

            for (JsonElement elem : jsonArray) {
                try {
                    elements.add(ElementLoader.load(elem.getAsJsonObject(), properties));
                } catch (InvalidConfigurationException e) {
                    configError("at step " + elements.size() + ": " + e.getMessage());
                } catch (IllegalStateException ignored) {}
            }

            // Parsing end data
            JsonObject endObject = JsonUtils.getObject(rootObject, "end");
            elements.add(EndStepLoader.load(endObject, properties));

        return new Parkour(parkourName, elements);
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
