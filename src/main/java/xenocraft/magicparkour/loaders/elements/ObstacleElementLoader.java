package xenocraft.magicparkour.loaders.elements;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.obstacle.ObstacleElement;
import xenocraft.magicparkour.utils.JsonUtils;

public class ObstacleElementLoader {

    public static ObstacleElement load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        Vector vector = JsonUtils.getVector(rootObject, "pos");
        Location loc = new Location(properties.world(), vector.getX(), vector.getY(), vector.getZ());

        return new ObstacleElement(loc);
    }
}
