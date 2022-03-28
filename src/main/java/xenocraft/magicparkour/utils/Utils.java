package xenocraft.magicparkour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.util.BoundingBox;

import org.jetbrains.annotations.Contract;

public class Utils {

    @Contract("null,_ -> fail")
    public static void assertNotNull(Object object, String msg) throws InvalidConfigurationException {
        if (object == null) throw new InvalidConfigurationException(msg);
    }

    public static boolean isValidIndex(List<?> list, int index) {
        return index >= 0 && index < list.size();
    }

    public static void showBox(BoundingBox box, World world) {
        List<Location> corners = new ArrayList<>();
        corners.add(new Location(world, box.getMaxX(), box.getMinY(), box.getMaxZ()));
        corners.add(new Location(world, box.getMaxX(), box.getMinY(), box.getMinZ()));
        corners.add(new Location(world, box.getMinX(), box.getMinY(), box.getMaxZ()));
        corners.add(new Location(world, box.getMinX(), box.getMinY(), box.getMinZ()));

        for (Location c : corners) {
            world.spawnParticle(Particle.FLAME, c, 1, 0, 0, 0, 0);
            c.add(0, box.getHeight(), 0);
            world.spawnParticle(Particle.FLAME, c, 1, 0, 0, 0, 0);
        }
    }
}
