package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.stream.*;

public class FoliaBlockStructure extends BlockStructure {

    private final EnhancedPlugin plugin;
    private final List<BlockChange> blockChanges;

    public FoliaBlockStructure(EnhancedPlugin plugin, List<BlockChange> blockChanges) {
        super(blockChanges);
        this.plugin = plugin;
        this.blockChanges = blockChanges;
    }

    public FoliaBlockStructure(EnhancedPlugin plugin, String name, @Nullable String group, List<BlockChange> blockChanges) {
        super(name, group, blockChanges);
        this.plugin = plugin;
        this.blockChanges = blockChanges;
    }

    public void place(@NotNull Location origin, @NotNull Rotation rotation) {
        for (BlockChange block : blockChanges) {
            Location location = block.getLocation(origin, rotation);
            plugin.getRegionScheduler(location).runTask(() -> {
                block.place(origin, rotation);
            });
        }
    }

    public void remove(@NotNull Location origin, @NotNull Rotation rotation) {
        for (BlockChange block : blockChanges) {
            Location location = block.getLocation(origin, rotation);
            plugin.getRegionScheduler(location).runTask(() -> {
                block.remove(origin, rotation);
            });
        }
    }

    public void subtract(@NotNull BlockStructure structure, @NotNull Location origin, @NotNull Rotation rotation) {
        Set<Location> newBlockLocations = structure.getBlockChanges().stream()
                .map(block -> block.getLocation(origin, rotation))
                .collect(Collectors.toSet());

        for (Block oldBlock : getOccupiedBlocks(origin, rotation)) {
            Location oldBlockLocation = oldBlock.getLocation();
            if (!newBlockLocations.contains(oldBlockLocation)) {
                plugin.getRegionScheduler(oldBlockLocation).runTask(() -> {
                    oldBlock.setType(Material.AIR, false);
                });
            }
        }
    }

}
