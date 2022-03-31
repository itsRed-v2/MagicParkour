package xenocraft.magicparkour.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.Main;
import xenocraft.magicparkour.PlayerManager;

public class ParkourCommand implements CommandExecutor {

    private final Main main;

    public ParkourCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) return true;

        if (args.length == 1) {
            switch (args[0]) {
                case "leave" -> leaveCommand(player);
                case "reload" -> reloadCommand(player);
                default -> unknownCommand(player);
            }
        }

        return true;
    }

    private void unknownCommand(Player player) {
        player.sendMessage("§cUnknown command. Type \"/jump help\" for a list of available commands");
    }

    private void leaveCommand(Player player) {
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerManager.getPlayer(uuid).leaveParkour();
            player.sendMessage("§6Left parkour!");
        } else {
            player.sendMessage("§cYour are not currently in a parkour");
        }
    }

    private void reloadCommand(Player player) {
        if (!player.hasPermission("magicparkour.reload")) {
            player.sendMessage("§cYou don't have the permission to run this command.");
            return;
        }

        player.sendMessage("§cPlease note that this command removes and recreates every parkour, " +
                "meaning that anyone currently parkouring gets kicked off their current parkour.");

        boolean success = main.reload();

        if (success) player.sendMessage("§aSuccessfully reloaded parkours");
        else player.sendMessage("§6There was an error while loading parkours. " +
                "All parkours may not be available. See the logs for more info.");
    }
}
