package xenocraft.magicparkour.services;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.data.Parkour;
import xenocraft.magicparkour.elements.CheckPoint;
import xenocraft.magicparkour.elements.steps.StartStep;
import xenocraft.magicparkour.elements.Step;

public class PlayerParkouring {

    private final Player player;
    private CheckPoint lastCheckPoint;
    private final StepIterator iterator;

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

    public void onMove() {
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
        }

        iterator.renderSteps();
    }
}
