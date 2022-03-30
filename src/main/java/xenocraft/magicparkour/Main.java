package xenocraft.magicparkour;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import xenocraft.magicparkour.commands.ParkourCommand;
import xenocraft.magicparkour.commands.ParkourTab;
import xenocraft.magicparkour.listeners.OnLeaveListener;
import xenocraft.magicparkour.listeners.ParkourStartListener;
import xenocraft.magicparkour.listeners.PlayerMoveListener;
import xenocraft.magicparkour.services.PlayerParkouring;
import xenocraft.magicparkour.tasks.ParkourInfoTitle;

public final class Main extends JavaPlugin {

    private File parkourConfigFile;

    @Override
    public void onEnable() {

        createParkourConfig();

        registerCommands();
        registerEvents();

        Main instance = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                ParkourManager.loadALL(parkourConfigFile);
                ParkourInfoTitle.init(instance);
            }
        }.runTaskLater(this, 10);
        
    }

    @Override
    public void onDisable() {
        for (PlayerParkouring parkouring : PlayerManager.players.values()) {
            parkouring.leaveParkour();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("parkour").setExecutor(new ParkourCommand());
            getCommand("parkour").setTabCompleter(new ParkourTab());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new ParkourStartListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new OnLeaveListener(), this);
    }

    private void createParkourConfig() {
        parkourConfigFile = new File(getDataFolder(), "parkour.json");

        if (!parkourConfigFile.exists()) saveResource(parkourConfigFile.getName(), false);
    }
}
