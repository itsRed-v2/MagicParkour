package xenocraft.magicparkour;

import java.util.List;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import xenocraft.magicparkour.steps.CheckPoint;
import xenocraft.magicparkour.steps.Step;

public class PlayerParkouring {

    public int step = -1;
    private final Player player;
    private final Parkour currentParkour;
    private CheckPoint lastCheckPoint;

    public PlayerParkouring(@NotNull Player player, @NotNull Parkour parkour) {
        this.player = player;
        this.currentParkour = parkour;
    }

    public Parkour getCurrentParkour() {
        return currentParkour;
    }

    public void leaveParkour() {
        int stepIndex = step;
        
        List<Step> reach = currentParkour.calculateStepReach(stepIndex, 2);
        for (Step step : reach) {
            step.hide();
        }

        PlayerManager.removePlayer(player.getUniqueId());
    }

    public CheckPoint getLastCheckPoint() {
        return lastCheckPoint;
    }

    public int getCheckIndex() {
        if (lastCheckPoint == null) return -1;
        else return lastCheckPoint.getCheckIndex();
    }

    public void setLastCheckPoint(CheckPoint lastCheckPoint) {
        this.lastCheckPoint = lastCheckPoint;
    }

    public Player getPlayer() {
        return player;
    }
}
