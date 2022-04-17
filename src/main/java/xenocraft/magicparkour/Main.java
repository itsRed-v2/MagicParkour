package xenocraft.magicparkour;

import java.io.File;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
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

    @Override
    public void onEnable() {

        saveDefaultConfig();
        createParkourConfig();

        registerCommands();
        registerEvents();

        loadLocale();

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
        for (PlayerParkouring parkouring : PlayerManager.players.values()) {
            parkouring.leaveParkour();
            parkouring.getPlayer().sendMessage("§cYou got kicked out of the parkour because the plugin was reloaded");
        }

        ParkourManager.parkours.clear();
        return ParkourManager.loadALL(parkourConfigFile);
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

    private void loadLocale() {
        Locale.setDefault(Locale.ENGLISH);

        ConfigurationSection config = getConfig();
        String language = config.getString("language", "en");
        Locale locale = new Locale(language);

        if (I18n.isSupported(locale)) {
            I18n.setLocale(locale);
        } else {
            Bukkit.getLogger().warning("[Magic-Parkour] Specified language is not supported: " + locale + ". Using English (en) instead.");
            I18n.setLocale(Locale.ENGLISH);
        }

        Bukkit.getLogger().info("[Magic-Parkour] Using locale: " + I18n.getLocale());
    }

    private void createParkourConfig() {
        parkourConfigFile = new File(getDataFolder(), "parkour.json");

        if (!parkourConfigFile.exists()) saveResource(parkourConfigFile.getName(), false);
    }
}
