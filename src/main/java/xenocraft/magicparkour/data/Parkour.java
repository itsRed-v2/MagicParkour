package xenocraft.magicparkour.data;

import java.util.List;

import org.bukkit.Location;

import xenocraft.magicparkour.elements.steps.CheckPointStep;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.steps.StartStep;
import xenocraft.magicparkour.elements.Step;

public record Parkour(String name, Location start, List<ParkourElement> elements, ParkourProperties properties) {

    public Parkour {
        elements.add(0, new StartStep(start, properties.startMaterial()));

        int stepNumber = -1;
        for (ParkourElement element : elements) {
            if (element instanceof Step) stepNumber++;
            
            if (element instanceof CheckPointStep check) {
                check.checkIndex = stepNumber;
            }
        }
    }
}
