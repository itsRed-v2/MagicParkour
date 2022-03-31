package xenocraft.magicparkour.data;

import java.util.List;

import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.Step;
import xenocraft.magicparkour.elements.steps.CheckPointStep;

public record Parkour(String name, List<ParkourElement> elements) {

    public Parkour {
        int stepNumber = -1;
        for (ParkourElement element : elements) {
            if (element instanceof Step) stepNumber++;
            
            if (element instanceof CheckPointStep check) {
                check.checkIndex = stepNumber;
            }
        }
    }
}
