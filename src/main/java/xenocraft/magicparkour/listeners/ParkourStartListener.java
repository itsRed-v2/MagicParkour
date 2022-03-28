package xenocraft.magicparkour.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import xenocraft.magicparkour.Parkour;
import xenocraft.magicparkour.ParkourManager;
import xenocraft.magicparkour.PlayerManager;

public class ParkourStartListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) return;

        for (Parkour parkour : ParkourManager.parkours) {
            
            if (parkour.steps().get(0).isValidPos(player.getLocation().toVector())) {
                PlayerManager.playerJoin(player, parkour);
                player.sendMessage("§eStarted parkour §6" + parkour.name());
                return;
            }
        }
    }
}
