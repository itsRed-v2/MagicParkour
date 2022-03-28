package xenocraft.magicparkour;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {

    public static final Map<UUID, PlayerParkouring> players = new HashMap<>();

    public static void playerJoin(Player player, Parkour parkour) {
        players.put(player.getUniqueId(), new PlayerParkouring(player, parkour));
    }

    public static PlayerParkouring getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public static boolean isPlayerRegistered(UUID uuid) {
        return players.containsKey(uuid);
    }
}
