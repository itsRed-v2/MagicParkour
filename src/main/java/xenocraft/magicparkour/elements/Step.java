package xenocraft.magicparkour.elements;

import org.bukkit.util.Vector;

public interface Step extends ParkourElement {
    
    boolean isValidPos(Vector vector);
    Vector getPosition();
    
}
