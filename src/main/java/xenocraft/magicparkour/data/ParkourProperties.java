package xenocraft.magicparkour.data;

import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import org.jetbrains.annotations.NotNull;

public record ParkourProperties(@NotNull World world,
                                @NotNull BlockData baseBlock,
                                @NotNull BlockData checkpointBlock,
                                @NotNull BlockData obstacleBlock) {}