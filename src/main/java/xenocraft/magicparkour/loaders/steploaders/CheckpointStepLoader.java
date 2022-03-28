package xenocraft.magicparkour.loaders.steploaders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.ParkourProperties;
import xenocraft.magicparkour.steps.CheckPointStep;
import xenocraft.magicparkour.utils.JsonUtils;

public class CheckpointStepLoader {

    public static CheckPointStep loadStep(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");
        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        Material material = JsonUtils.getMaterial(rootObject, "material", properties.checkpointMaterial());

        return new CheckPointStep(loc, material);
    }
}
