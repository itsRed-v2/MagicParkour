package xenocraft.magicparkour.elements.obstacle;

import org.bukkit.Location;

import xenocraft.magicparkour.elements.ParkourElement;

public class ObstacleElement implements ParkourElement {

    private final Location location;
    private final ObstacleBlock[] blocks;

    private boolean visible = false;

    public ObstacleElement(Location location, ObstacleBlock[] blocks) {
        this.location = location.toBlockLocation();
        this.blocks = blocks;
    }

    @Override
    public boolean takesScope() {
        return false;
    }

    @Override
    public void show() {
        if (visible) return;
        visible = true;

        for (ObstacleBlock block : blocks) {
            block.place(location);
        }
    }

    @Override
    public void hide() {
        if (!visible) return;
        visible = false;

        for (ObstacleBlock block : blocks) {
            block.remove(location);
        }
    }
}
