package xenocraft.magicparkour;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class Msg {

    private static Main main;

    private Msg() {}

    public static void init(Main main) {
        Msg.main = main;
    }

    public static String get(String key) {
        FileConfiguration messagesConfig = main.getMessagesConfig();
        Configuration defaultMessages = messagesConfig.getDefaults();
        assert defaultMessages != null;
        
        return messagesConfig.getString(key, defaultMessages.getString(key));
    }

}
