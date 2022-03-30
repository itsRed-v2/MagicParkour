package xenocraft.magicparkour.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import xenocraft.magicparkour.Main;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.services.PlayerParkouring;

@SuppressWarnings("ClassCanBeRecord")
public class PlayerMoveListener implements Listener {

    private final Main main;

    public PlayerMoveListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerParkouring parkouring = PlayerManager.getPlayer(uuid);
            parkouring.onMove(main);
        }
    }

}
