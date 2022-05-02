package xenocraft.magicparkour.elements.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.Step;

public class SlimeStep implements Step {

    private final Location location;
    private final int sizeX;
    private final int sizeZ;
    private final boolean takesScope;

    private boolean visible = false;

    public SlimeStep(Location location, int sizeX, int sizeZ, boolean scope) {
        this.location = location.toBlockLocation();
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        takesScope = scope;
    }

    @Override
    public boolean takesScope() {
        return takesScope;
    }

    @Override
    public boolean isValidPos(Vector vector) {
        BoundingBox box = location.getBlock().getBoundingBox();
        box.shift(0, 1, 0);
        box.expandDirectional(sizeX - 1, 0, sizeZ - 1);
        box.expand(.3, 0, .3);

        return box.contains(vector);
    }

    @Override
    public Vector getPosition() {
        return location.toVector();
    }

    @Override
    public void show() {
        if (visible) return;
        visible = true;

        fill(Material.SLIME_BLOCK);
    }

    @Override
    public void hide() {
        if (!visible) return;
        visible = false;
        
        fill(Material.AIR);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SlimeStep step
                && location.equals(step.location)
                && sizeX == step.sizeX
                && sizeZ == step.sizeZ
                && takesScope == step.takesScope;
    }

    private void fill(Material material) {
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                location.clone().add(x, 0, z).getBlock().setType(material);
            }
        }
    }
}
