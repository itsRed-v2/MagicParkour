package xenocraft.magicparkour;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import xenocraft.magicparkour.steps.CheckPointStep;
import xenocraft.magicparkour.steps.StartStep;
import xenocraft.magicparkour.steps.Step;
import xenocraft.magicparkour.utils.Utils;

public record Parkour(String name, Location start, List<Step> steps, ParkourProperties properties) {

    public Parkour {
        steps.add(0, new StartStep(start, properties.startMaterial()));

        int stepNumber = -1;
        for (Step step : steps) {
            stepNumber++;
            if (step instanceof CheckPointStep check) {
                check.checkIndex = stepNumber;
            }
        }
    }

    public List<Step> calculateStepReach(int startIndex, int scope) {
        List<Step> stepsReach = new ArrayList<>();
        if (Utils.isValidIndex(steps, startIndex)) stepsReach.add(steps.get(startIndex));

        int stepIndex = startIndex;
        int tempScope = scope;
        while (tempScope > 0) {
            stepIndex--;
            if (!Utils.isValidIndex(steps, stepIndex)) break;
            
            Step current = steps.get(stepIndex);
            stepsReach.add(0, current);

            if (current.takesScope()) tempScope--;
        }

        stepIndex = startIndex;
        tempScope = scope;
        while (tempScope > 0) {
            stepIndex++;
            if (!Utils.isValidIndex(steps, stepIndex)) break;

            Step current = steps.get(stepIndex);
            stepsReach.add(current);

            if (current.takesScope()) tempScope--;
        }

        return stepsReach;
    }
}
