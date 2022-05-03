package xenocraft.magicparkour.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xenocraft.magicparkour.PlayerManager;

public class ParkourTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return new ArrayList<>();

        List<String> strings = new ArrayList<>();
        
        if (args.length == 1) {
            if (PlayerManager.isPlayerRegistered(player.getUniqueId())) {
                strings.add("leave");
                strings.add("checkpoint");
            }
            if (player.hasPermission("magicparkour.reload")) strings.add("reload");
            strings.add("help");

            strings.removeIf(str -> !str.toLowerCase().startsWith(args[0].toLowerCase()));
        }

        return strings;
    }
}
