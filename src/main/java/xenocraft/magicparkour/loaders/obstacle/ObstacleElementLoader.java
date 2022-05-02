package xenocraft.magicparkour.loaders.obstacle;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.obstacle.ObstacleElement;
import xenocraft.magicparkour.utils.JsonUtils;

public class ObstacleElementLoader {

    public static ObstacleElement load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");
        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        JsonArray blocks = JsonUtils.getArray(rootObject, "blocks");

        ObstacleElement.ObstacleBlock[] blockArray = new ObstacleElement.ObstacleBlock[blocks.size()];

        for (int i = 0; i < blocks.size(); i++) {
            JsonObject block = JsonUtils.getArrayObject(blocks, i);
            blockArray[i] = loadBlock(block, properties);
        }

        return new ObstacleElement(loc, blockArray);
    }
    
    private static ObstacleElement.ObstacleBlock loadBlock(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector offset = JsonUtils.getVector(rootObject, "offset");
        BlockData block = JsonUtils.getBlockData(rootObject, "block", properties.obstacleBlock());

        return new ObstacleElement.ObstacleBlock(block, offset);
    }
}
