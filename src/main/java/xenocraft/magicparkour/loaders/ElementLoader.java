package xenocraft.magicparkour.loaders;

import org.bukkit.configuration.InvalidConfigurationException;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.data.ParkourProperties;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.loaders.elements.CheckpointStepLoader;
import xenocraft.magicparkour.loaders.elements.ObstacleElementLoader;
import xenocraft.magicparkour.loaders.elements.SimpleStepLoader;
import xenocraft.magicparkour.loaders.elements.SlimeStepLoader;
import xenocraft.magicparkour.utils.JsonUtils;

public class ElementLoader {

    public static ParkourElement load(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        String typeString = JsonUtils.getString(rootObject, "type", "simple");

        return switch (typeString) {
            case "simple" -> SimpleStepLoader.load(rootObject, properties);
            case "slime" -> SlimeStepLoader.load(rootObject, properties);
            case "checkpoint" -> CheckpointStepLoader.load(rootObject, properties);
            case "obstacle" -> ObstacleElementLoader.load(rootObject, properties);

            default -> throw new InvalidConfigurationException("this step type doesn't exist: \"" + typeString + "\"");
        };
    }
}
