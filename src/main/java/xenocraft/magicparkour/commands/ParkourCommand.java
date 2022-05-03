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

        if (args.length < 1) player.sendMessage(I18n.getMessage("command.missingArg"));

        else if (args.length == 1) {
            switch (args[0]) {
                case "leave" -> leaveCommand(player);
                case "checkpoint" -> checkpointCommand(player);
                case "help" -> helpCommand(player);
                case "reload" -> reloadCommand(player);
                default -> unknownCommand(player);
            }
        }

        else player.sendMessage(I18n.getMessage("command.tooManyArgs"));
        
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
            player.sendMessage(I18n.getMessage("parkour.notInParkour"));
        }
    }

    private void checkpointCommand(Player player) {
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerManager.getPlayer(uuid).tpToCheckpoint(main);
            player.sendMessage(I18n.getMessage("parkour.toCheckpoint"));
        } else {
            player.sendMessage(I18n.getMessage("parkour.notInParkour"));
        }
    }

    private void helpCommand(Player player) {
        player.sendMessage(I18n.getMessage("command.help"));
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
