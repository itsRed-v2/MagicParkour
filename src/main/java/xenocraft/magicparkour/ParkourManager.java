package xenocraft.magicparkour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.loaders.ParkourLoader;

public class ParkourManager {

    public static final Set<Parkour> parkours = new HashSet<>();

    public static boolean loadALL(File parkourConfigFile) {

        boolean successful = true;

        JsonElement config;
        try {
            config = JsonParser.parseReader(new FileReader(parkourConfigFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        if (!config.isJsonObject()) return true;
        JsonObject configObject = config.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : configObject.entrySet()) {
            JsonElement jsonParkour = entry.getValue();
            String parkourID = entry.getKey();

            try {
                ParkourManager.parkours.add(ParkourLoader.loadParkour(jsonParkour, parkourID));
            } catch (InvalidConfigurationException e) {
                Bukkit.getLogger().warning(String.format("[Magic-Parkour] Invalid config in parkour \"%s\": %s", parkourID, e.getMessage()));
                successful = false;
            }
        }

        // placing start and end of parkours
        for (Parkour parkour : ParkourManager.parkours) {
            List<ParkourElement> elements = parkour.elements();

            elements.get(0).show();
            elements.get(elements.size() - 1).show();
        }

        return successful;
    }
}
