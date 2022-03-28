package xenocraft.magicparkour.loaders;

import org.bukkit.configuration.InvalidConfigurationException;

import com.google.gson.JsonObject;
import xenocraft.magicparkour.ParkourProperties;
import xenocraft.magicparkour.loaders.steploaders.CheckpointStepLoader;
import xenocraft.magicparkour.loaders.steploaders.SimpleStepLoader;
import xenocraft.magicparkour.loaders.steploaders.SlimeStepLoader;
import xenocraft.magicparkour.steps.Step;
import xenocraft.magicparkour.utils.JsonUtils;

public class StepLoader {

    public static Step loadStep(JsonObject rootObject, ParkourProperties properties) throws InvalidConfigurationException {

        String typeString = JsonUtils.getString(rootObject, "type", "simple");

        return switch (typeString) {
            case "simple" -> SimpleStepLoader.loadStep(rootObject, properties);
            case "slime" -> SlimeStepLoader.loadStep(rootObject, properties);
            case "checkpoint" -> CheckpointStepLoader.loadStep(rootObject, properties);

            default -> throw new InvalidConfigurationException("this step type doesn't exist: \"" + typeString + "\"");
        };
    }
}
