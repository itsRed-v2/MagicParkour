package xenocraft.magicparkour.loaders.obstacle;

import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.elements.obstacle.ObstacleBlock;
import xenocraft.magicparkour.utils.JsonUtils;

public class ObstacleBlockLoader {

    public static ObstacleBlock load(JsonObject rootObject) throws InvalidConfigurationException {

        Vector offset = JsonUtils.getVector(rootObject, "offset");
        BlockData block = JsonUtils.getBlockData(rootObject, "block");
        
        return new ObstacleBlock(block, offset);
    }
}
