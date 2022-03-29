package xenocraft.magicparkour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.loaders.ParkourLoader;

public class ParkourManager {

    public static final Set<Parkour> parkours = new HashSet<>();

    public static void loadALL(File parkourConfigFile) {

        JsonElement config;

        try {
            config = JsonParser.parseReader(new FileReader(parkourConfigFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (!config.isJsonObject()) return;
        JsonObject configObject = config.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : configObject.entrySet()) {
            JsonElement jsonParkour = entry.getValue();
            String parkourID = entry.getKey();

            try {
                ParkourManager.parkours.add(ParkourLoader.loadParkour(jsonParkour, parkourID));
            } catch (InvalidConfigurationException e) {
                Bukkit.getLogger().warning("[Magic-Parkour] Invalid config: " + e.getMessage());
            }
        }
    }
}
