package xenocraft.magicparkour.elements.obstacle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class ObstacleBlock {

    private final BlockData block;
    private final BlockVector offset;

    public ObstacleBlock(BlockData blockData, Vector offset) {
        block = blockData;
        this.offset = offset.toBlockVector();
    }

    public void place(Location origin) {
        Location position = origin.clone().add(offset);
        position.getBlock().setBlockData(block, false);
    }

    public void remove(Location origin) {
        Location position = origin.clone().add(offset);
        position.getBlock().setType(Material.AIR, false);
    }
}
