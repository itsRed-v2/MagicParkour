package xenocraft.magicparkour.steps;

import org.bukkit.entity.Player;

public interface CheckPoint {

    void teleportPlayer(Player player);
    int getCheckIndex();
}
