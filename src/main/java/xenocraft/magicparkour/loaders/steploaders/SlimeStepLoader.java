package xenocraft.magicparkour.loaders.steploaders;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.ParkourProperties;
import xenocraft.magicparkour.steps.SlimeStep;
import xenocraft.magicparkour.utils.JsonUtils;

public class SlimeStepLoader {

    public static SlimeStep loadStep(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");

        int sizeX = JsonUtils.getInt(rootObject, "sizeX", 1);
        int sizeZ = JsonUtils.getInt(rootObject, "sizeZ", 1);

        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        return new SlimeStep(loc, sizeX, sizeZ);
    }
}
