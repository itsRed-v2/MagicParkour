package xenocraft.magicparkour.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.text.Component;
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

                    String builder = "§eStep §6" + step +
                            " §f| §2Checkpoint §a" + lastCheckPoint +
                            "§f | §7/parkour help";

                    player.sendActionBar(Component.text(builder));
                }
            }
        }.runTaskTimer(main, 2, 2);
    }
}
