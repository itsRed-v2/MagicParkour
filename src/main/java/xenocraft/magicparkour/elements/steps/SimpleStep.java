package xenocraft.magicparkour.elements.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.Step;

public class SimpleStep implements Step {

    private final Location location;
    private final BlockData block;
    private final boolean takesScope;

    private boolean visible = false;

    public SimpleStep(Location location, BlockData blockData, boolean scope) {
        this.location = location.toBlockLocation();
        block = blockData;
        takesScope = scope;
    }

    @Override
    public boolean takesScope() {
        return takesScope;
    }

    @Override
    public boolean isValidPos(Vector vector) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        BoundingBox box = new BoundingBox(x, y, z, x+1, y+2, z+1);
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
        location.getBlock().setBlockData(block);
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
                && block.equals(step.block)
                && location.equals(step.location)
                && takesScope == step.takesScope;
    }
}
