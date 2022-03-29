package xenocraft.magicparkour.elements.obstacle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

public class ParkourBlock {

    private BlockData blockData;
    private Vector offset;

    ParkourBlock(String strBlockData, Vector offset) {
        blockData = Bukkit.createBlockData(strBlockData);
        this.offset = offset.toBlockVector();
    }

    public void place(Location origin) {
        Location position = origin.add(offset);
        position.getBlock().setBlockData(blockData);
    }

    public void remove(Location origin) {
        Location position = origin.add(offset);
        position.getBlock().setType(Material.AIR);
    }
}
