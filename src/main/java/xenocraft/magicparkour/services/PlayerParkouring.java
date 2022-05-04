package xenocraft.magicparkour.services;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.Msg;
import xenocraft.magicparkour.Main;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.elements.CheckPoint;
import xenocraft.magicparkour.elements.Step;
import xenocraft.magicparkour.elements.steps.EndStep;
import xenocraft.magicparkour.elements.steps.StartStep;

public class PlayerParkouring {

    private final Player player;
    private CheckPoint lastCheckPoint;
    private final StepIterator iterator;

    private boolean fallCheck = true;

    public PlayerParkouring(@NotNull Player player, @NotNull Parkour parkour) {
        this.player = player;
        lastCheckPoint = (StartStep) parkour.elements().get(0);
        iterator = new StepIterator(parkour.elements());
    }
    
    public void leaveParkour() {
        iterator.hideSteps();

        PlayerManager.removePlayer(player.getUniqueId());
    }

    public int getStepIndex() {
        return iterator.getStepIndex();
    }

    public int getCheckIndex() {
        return lastCheckPoint.getCheckIndex();
    }

    public Player getPlayer() {
        return player;
    }

    public void onMove(Main main) {
        Location playerLoc = player.getLocation();
        Vector playerVec = playerLoc.toVector();

        Step next = iterator.getNext();
        if (next != null && next.isValidPos(playerVec)) {
            iterator.increment();

            player.playSound(playerLoc, Sound.BLOCK_LEVER_CLICK, 1, 1);

            if (next instanceof CheckPoint check) {
                lastCheckPoint = check;
                player.playSound(playerLoc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            else if (next instanceof EndStep) {
                iterator.renderSteps();
                leaveParkour();

                player.sendMessage(Msg.get("parkour.finish"));
                player.playSound(playerLoc, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.getWorld().spawnParticle(Particle.TOTEM, playerLoc, 50, 0, 0, 0, .5);

                return;
            }
        }

        // checking for fall
        if (fallCheck) {
            int minHeight = iterator.getCurrent().getPosition().getBlockY();

            next = iterator.getNext();
            if (next != null) {
                minHeight = Math.min(minHeight, next.getPosition().getBlockY());
            }

            minHeight -= 5;

            if (playerVec.getY() < minHeight) {
                tpToCheckpoint(main);
            }
        }

        // rendering steps
        iterator.renderSteps();
    }

    public void tpToCheckpoint(Main main) {
        iterator.hideSteps();
        iterator.teleport(lastCheckPoint.getCheckIndex());
        iterator.renderSteps();

        fallCheck = false;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setFallDistance(0);
                lastCheckPoint.teleportPlayer(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, .5f);

                fallCheck = true;
            }
        }.runTaskLater(main, 5);
    }
}
