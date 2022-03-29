package xenocraft.magicparkour.elements.obstacle;

import org.bukkit.Location;
import org.bukkit.Material;

import xenocraft.magicparkour.elements.ParkourElement;

public class ObstacleElement implements ParkourElement {

    private final Location location;

    private boolean visible = false;

    public ObstacleElement(Location location) {
        this.location = location.toBlockLocation();
    }

    @Override
    public boolean takesScope() {
        return false;
    }

    @Override
    public void show() {
        if (visible) return;
        visible = true;
        location.getBlock().setType(Material.WHITE_STAINED_GLASS);
    }

    @Override
    public void hide() {
        if (!visible) return;
        visible = false;
        location.getBlock().setType(Material.AIR);
    }
}
