package xenocraft.magicparkour.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static xenocraft.magicparkour.utils.Utils.isValidIndex;
import xenocraft.magicparkour.Parkour;
import xenocraft.magicparkour.PlayerManager;
import xenocraft.magicparkour.PlayerParkouring;
import xenocraft.magicparkour.steps.CheckPoint;
import xenocraft.magicparkour.steps.Step;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (PlayerManager.isPlayerRegistered(uuid)) {
            PlayerParkouring parkouring = PlayerManager.getPlayer(uuid);

            boolean isNextStep = checkCurrentStep(parkouring);

            Parkour parkour = parkouring.getCurrentParkour();
            List<Step> steps = parkour.steps();
            int stepIndex = parkouring.step;

            if (isNextStep) {
                Step currentStep = steps.get(stepIndex);

                if (currentStep instanceof CheckPoint checkPoint
                        && parkouring.getCheckIndex() < checkPoint.getCheckIndex()) {
                    parkouring.setLastCheckPoint(checkPoint);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                }
            }

            // Showing/Hiding steps
            List<Step> unReachable = parkour.calculateStepReach(stepIndex, 3);
            List<Step> reachable = parkour.calculateStepReach(stepIndex, 2);

            for (Step step : unReachable) {
                if (reachable.contains(step)) step.show();
                else step.hide();
            }
            
        }
    }

    private boolean checkCurrentStep(PlayerParkouring parkouring) {
        Player player = parkouring.getPlayer();
        Location playerLoc = player.getLocation();
        List<Step> steps = parkouring.getCurrentParkour().steps();
        int stepIndex = parkouring.step;

        if (isValidIndex(steps,stepIndex + 1) && steps.get(stepIndex + 1).isValidPos(playerLoc.toVector())) {
            parkouring.step++;
            player.playSound(playerLoc, Sound.BLOCK_LEVER_CLICK, 1, 1);
            return true;
        }
        else if (isValidIndex(steps,stepIndex - 1) && steps.get(stepIndex - 1).isValidPos(playerLoc.toVector())) {
            parkouring.step--;
            player.playSound(playerLoc, Sound.BLOCK_LEVER_CLICK, 1, .5f);
        }
        return false;
    }

}
