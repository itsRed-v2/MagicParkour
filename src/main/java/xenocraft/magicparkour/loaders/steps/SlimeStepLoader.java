package xenocraft.magicparkour.loaders.steps;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.steps.SlimeStep;
import xenocraft.magicparkour.utils.JsonUtils;

public class SlimeStepLoader {

    public static SlimeStep load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");

        int sizeX = JsonUtils.getInt(rootObject, "sizeX", 1);
        int sizeZ = JsonUtils.getInt(rootObject, "sizeZ", 1);

        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        boolean scope = JsonUtils.getBool(rootObject, "scope", false);

        return new SlimeStep(loc, sizeX, sizeZ, scope);
    }
}
