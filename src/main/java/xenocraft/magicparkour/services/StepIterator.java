package xenocraft.magicparkour.services;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import xenocraft.magicparkour.elements.ParkourElement;
import xenocraft.magicparkour.elements.Step;

public class StepIterator {

    private final ParkourElement[] elements;
    private int currentIndex = 0;
    private int currentStepIndex = 0;

    StepIterator(List<ParkourElement> elementList) {
        elements = elementList.toArray(new ParkourElement[0]);

        while (!(elements[currentIndex] instanceof Step)) {
            currentIndex++;
        }
    }

    public int getStepIndex() {
        return currentStepIndex;
    }

    public @Nullable Step getNext() {
        int index = currentIndex;
        while (true) {
            index++;
            if (!isValidIndex(index)) return null;

            ParkourElement elem = elements[index];
            if (elem instanceof Step step) return step;
        }
    }

    public void increment() {
        move(1);
    }
    public void decrement() {
        move(-1);
    }
    public void teleport(int stepIndex) {
        move(stepIndex - currentStepIndex);
    }

    private void move(int distance) {
        int dir;
        if (distance > 0) dir = 1;
        else if (distance < 0) dir = -1;
        else return;

        while (distance != 0) {
            int index = currentIndex;
            do {
                index += dir;
                if (!isValidIndex(index)) return;
            } while (!(elements[index] instanceof Step));

            currentIndex = index;
            currentStepIndex += dir;
            distance -= dir;
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < elements.length;
    }

    public void renderSteps() {
        List<ParkourElement> reachable = calculateReach(2);
        List<ParkourElement> unReachable = calculateReach(3);
        
        for (ParkourElement element : unReachable) {
            if (reachable.contains(element)) element.show();
            else element.hide();
        }
    }
    public void hideSteps() {
        for (ParkourElement element : calculateReach(2)) {
            element.hide();
        }
    }

    public List<ParkourElement> calculateReach(int scope) {
        List<ParkourElement> reach = new ArrayList<>();
        reach.add(elements[currentIndex]);

        int index = currentIndex;
        int tempScope = scope;
        while (tempScope > 0) {
            index--;
            if (!isValidIndex(index)) break;

            ParkourElement current = elements[index];
            reach.add(0, current);

            if (current.takesScope()) tempScope--;
        }

        index = currentIndex;
        tempScope = scope;
        while (tempScope > 0) {
            index++;
            if (!isValidIndex(index)) break;

            ParkourElement current = elements[index];
            reach.add(current);

            if (current.takesScope()) tempScope--;
        }

        return reach;
    }
}
