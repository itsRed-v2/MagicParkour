package xenocraft.magicparkour.steps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class StartStep implements Step, CheckPoint {

    private final Location location;
    private final Material material;

    private boolean visible = false;

    public StartStep(Location location, Material material) {
        this.location = location.toBlockLocation();
        this.material = material;
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
    public void show() {
        if (visible) return;
        visible = true;

        location.getBlock().setType(material);
    }

    @Override
    public void hide() {
        // StartStep is persistent
    }

    @Override
    public void teleportPlayer(Player player) {
        player.teleport(location.add(.5, 1, .5));
    }

    @Override
    public int getCheckIndex() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StartStep start
                && location.equals(start.location)
                && material == start.material;
    }
}
