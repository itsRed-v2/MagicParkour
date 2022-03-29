package xenocraft.magicparkour.elements;

import org.bukkit.entity.Player;

public interface CheckPoint {

    void teleportPlayer(Player player);
    int getCheckIndex();
    
}
