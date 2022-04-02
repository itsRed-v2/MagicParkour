package xenocraft.magicparkour.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import xenocraft.magicparkour.PlayerManager;

public class WorldChangeListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerManager.getPlayer(uuid).leaveParkour();
            player.sendMessage("§6/!\\ §cYou left the parkour by changing world.");
        }
    }
}
