package xenocraft.magicparkour.data;

import org.bukkit.Material;
import org.bukkit.World;

import org.jetbrains.annotations.NotNull;

public record ParkourProperties(@NotNull World world,
                                @NotNull Material blockMaterial,
                                @NotNull Material checkpointMaterial,
                                @NotNull Material startMaterial) {}