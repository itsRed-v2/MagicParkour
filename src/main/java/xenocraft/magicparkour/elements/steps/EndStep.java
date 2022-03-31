package xenocraft.magicparkour.elements.steps;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.Step;

public class EndStep implements Step {

    private final Location location;
    private final BlockData block;

    private boolean visible = false;

    public EndStep(Location location, BlockData blockData) {
        this.location = location.toBlockLocation();
        block = blockData;
    }

    @Override
    public boolean takesScope() {
        return true;
    }

    @Override
    public boolean isValidPos(Vector vector) {
        BlockVector bVector = vector.toBlockVector();
        BlockVector bLoc = location.toVector().add(new Vector(0, 1, 0)).toBlockVector();

        return bVector.equals(bLoc);
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
        // EndStep is persistent
    }

}
