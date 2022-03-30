package xenocraft.magicparkour.elements.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.CheckPoint;
import xenocraft.magicparkour.elements.Step;

public class CheckPointStep implements Step, CheckPoint {

    private final Location location;
    private final BlockData block;
    public int checkIndex;

    private boolean visible = false;

    public CheckPointStep(Location location, BlockData blockData) {
        this.location = location.toBlockLocation();
        block = blockData;
    }

    @Override
    public boolean takesScope() {
        return true;
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
                && block.equals(step.block)
                && location.equals(step.location);
    }
}
