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
    private final boolean takesScope;
    public int checkIndex;

    private boolean visible = false;

    public CheckPointStep(Location location, BlockData blockData, boolean scope) {
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

    public void teleportPlayer(Player player) {
        Location playerLoc = player.getLocation();

        Location tpLoc = location.clone().add(.5, 1, .5);
        tpLoc.setYaw(playerLoc.getYaw());
        tpLoc.setPitch(playerLoc.getPitch());
        
        player.teleport(tpLoc);
    }

    @Override
    public int getCheckIndex() {
        return checkIndex;
    }
}
