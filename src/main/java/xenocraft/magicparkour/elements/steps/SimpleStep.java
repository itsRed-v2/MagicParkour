package xenocraft.magicparkour.elements.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.Step;

public class SimpleStep implements Step {

    private final Location location;
    private final Material material;

    private boolean visible = false;

    public SimpleStep(Location location, Material material) {
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimpleStep step
                && material == step.material
                && location.equals(step.location);
    }
}
