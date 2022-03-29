package xenocraft.magicparkour.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.services.PlayerParkouring;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerParkouring parkouring = PlayerManager.getPlayer(uuid);
            parkouring.onMove();
        }
    }

}
