package xenocraft.magicparkour.elements.obstacle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import xenocraft.magicparkour.elements.ParkourElement;

public class ObstacleElement implements ParkourElement {

    public static class ObstacleBlock {

        private final BlockData blockData;
        private final BlockVector offset;

        public ObstacleBlock(BlockData blockData, Vector offset) {
            this.blockData = blockData;
            this.offset = offset.toBlockVector();
        }

        public void place(Location origin) {
            Location position = origin.clone().add(offset);
            position.getBlock().setBlockData(blockData, false);
        }

        public void remove(Location origin) {
            Location position = origin.clone().add(offset);
            position.getBlock().setType(Material.AIR, false);
        }
    }

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
