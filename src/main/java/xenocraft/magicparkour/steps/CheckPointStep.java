package xenocraft.magicparkour.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class CheckPointStep implements Step, CheckPoint {

    private final Location location;
    private final Material material;
    public int checkIndex;

    private boolean visible = false;

    public CheckPointStep(Location location, Material material) {
        this.location = location.toBlockLocation();
        this.material = material;
    }

    @Override
    public boolean takesScope() {
        return true;
    }

    @Override
    public boolean isValidPos(Vector vector) {
        BoundingBox box = location.getBlock().getBoundingBox();
        box.shift(0, 1, 0);
        box.expand(.3, 0, .3);
        return box.contains(vector);
    }

    @Override
    public void show() {
        if (visible) return;
        visible = true;
        location.getBlock().setType(material);
    }

    @Override
    public void hide() {
        if (!visible) return;
        visible = false;
        location.getBlock().setType(Material.AIR);
    }

    public void teleportPlayer(Player player) {
        player.teleport(location.add(.5, 1, .5));
    }

    @Override
    public int getCheckIndex() {
        return checkIndex;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CheckPointStep step
                && material == step.material
                && location.equals(step.location);
    }
}
