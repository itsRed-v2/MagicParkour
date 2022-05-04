package xenocraft.magicparkour;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import xenocraft.magicparkour.commands.ParkourCommand;
import xenocraft.magicparkour.commands.ParkourTab;
import xenocraft.magicparkour.listeners.OnLeaveListener;
import xenocraft.magicparkour.listeners.ParkourStartListener;
import xenocraft.magicparkour.listeners.PlayerMoveListener;
import xenocraft.magicparkour.listeners.WorldChangeListener;
import xenocraft.magicparkour.services.PlayerParkouring;
import xenocraft.magicparkour.tasks.ParkourInfoTitle;

public final class Main extends JavaPlugin {

    private File parkourConfigFile;
    private File messagesConfigFile;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        createConfig();
        Msg.init(this);

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

    public boolean reload() {
        boolean msgSuccess = loadMessages();

        for (PlayerParkouring parkouring : PlayerManager.players.values()) {
            parkouring.leaveParkour();
            parkouring.getPlayer().sendMessage(Msg.get("reload.kick"));
        }

        ParkourManager.parkours.clear();
        return ParkourManager.loadALL(parkourConfigFile) && msgSuccess;
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("parkour").setExecutor(new ParkourCommand(this));
            getCommand("parkour").setTabCompleter(new ParkourTab());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new ParkourStartListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new OnLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(), this);
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    private void createConfig() {
        parkourConfigFile = new File(getDataFolder(), "parkour.json");
        if (!parkourConfigFile.exists()) saveResource(parkourConfigFile.getName(), false);

        messagesConfigFile = new File(getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) saveResource(messagesConfigFile.getName(), false);

        messagesConfig = new YamlConfiguration();
        loadMessages();
    }

    private boolean loadMessages() {
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
