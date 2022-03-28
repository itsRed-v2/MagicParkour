package xenocraft.magicparkour.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.PlayerManager;

public class ParkourCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        UUID uuid = player.getUniqueId();

        if (args.length < 1) return true;

        if (args.length == 1) {
            if (args[0].equals("leave")) {
                if (PlayerManager.isPlayerRegistered(uuid)) {
                    PlayerManager.getPlayer(uuid).leaveParkour();
                    player.sendMessage("§6Left parkour!");
                } else {
                    player.sendMessage("§cYour are not currently in a parkour");
                }
            }
        }

        return true;
    }
}
