package xenocraft.magicparkour.loaders.obstacle;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.obstacle.ObstacleBlock;
import xenocraft.magicparkour.elements.obstacle.ObstacleElement;
import xenocraft.magicparkour.utils.JsonUtils;

public class ObstacleElementLoader {

    public static ObstacleElement load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");
        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        JsonArray blocks = JsonUtils.getArray(rootObject, "blocks");

        ObstacleBlock[] blockArray = new ObstacleBlock[blocks.size()];

        for (int i = 0; i < blocks.size(); i++) {
            JsonObject block = JsonUtils.getArrayObject(blocks, i);
            blockArray[i] = ObstacleBlockLoader.load(block);
        }

        return new ObstacleElement(loc, blockArray);
    }
}
