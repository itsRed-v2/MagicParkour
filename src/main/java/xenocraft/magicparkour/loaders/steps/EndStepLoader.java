package xenocraft.magicparkour.loaders.steps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.steps.EndStep;
import xenocraft.magicparkour.utils.JsonUtils;

public class EndStepLoader {

    public static EndStep load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");
        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        BlockData block = JsonUtils.getBlockData(rootObject, "block", Bukkit.createBlockData(Material.DIAMOND_BLOCK));

        return new EndStep(loc, block);
    }
}
