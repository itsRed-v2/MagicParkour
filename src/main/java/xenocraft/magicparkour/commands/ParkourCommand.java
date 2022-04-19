package xenocraft.magicparkour.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.I18n;
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
        player.sendMessage(I18n.getMessage("command.unknown"));
    }

    private void leaveCommand(Player player) {
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerManager.getPlayer(uuid).leaveParkour();
            player.sendMessage(I18n.getMessage("parkour.leave"));
        } else {
            player.sendMessage(I18n.getMessage("parkour.alreadyOut"));
        }
    }

    private void reloadCommand(Player player) {
        if (!player.hasPermission("magicparkour.reload")) {
            player.sendMessage(I18n.getMessage("command.noPermission"));
            return;
        }

        player.sendMessage(I18n.getMessage("reload.warn"));

        boolean success = main.reload();

        player.sendMessage(I18n.getMessage(success ? "reload.success" : "reload.fail"));
    }
}
