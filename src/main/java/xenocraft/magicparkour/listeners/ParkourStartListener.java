package xenocraft.magicparkour.listeners;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.I18n;
import xenocraft.magicparkour.ParkourManager;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.elements.steps.StartStep;

public class ParkourStartListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) return;

        Vector playerVec = player.getLocation().toVector();

        for (Parkour parkour : ParkourManager.parkours) {

            StartStep start = (StartStep) parkour.elements().get(0);

            if (start.isValidPos(playerVec)) {
                PlayerManager.playerJoin(player, parkour);
                player.sendMessage(String.format(I18n.getMessage("parkour.start"), parkour.name()));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                return;
            }
        }
    }
}
