package xenocraft.magicparkour.steps;

import org.bukkit.util.Vector;

public interface Step {

    boolean takesScope();

    boolean isValidPos(Vector vector);

    void show();
    void hide();
}
