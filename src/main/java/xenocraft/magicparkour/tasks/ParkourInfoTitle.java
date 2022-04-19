package xenocraft.magicparkour.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.text.Component;

import xenocraft.magicparkour.I18n;
import xenocraft.magicparkour.Main;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.services.PlayerParkouring;

public class ParkourInfoTitle {

    private static boolean inited = false;

    public static void init(Main main) {
        if (inited) return;
        inited = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : PlayerManager.players.keySet()) {
                    Player player = Bukkit.getPlayer(uuid);

                    if (player == null) continue;

                    PlayerParkouring parkouring = PlayerManager.getPlayer(uuid);
                    int step = parkouring.getStepIndex();
                    int lastCheckPoint = parkouring.getCheckIndex();

                    String title = String.format("§e%s §6%d §f| §2%s §a%d §f| §7/parkour help",
                            I18n.getMessage("parkour.title.step"),
                            step,
                            I18n.getMessage("parkour.title.checkpoint"),
                            lastCheckPoint);

                    player.sendActionBar(Component.text(title));
                }
            }
        }.runTaskTimer(main, 2, 2);
    }
}
