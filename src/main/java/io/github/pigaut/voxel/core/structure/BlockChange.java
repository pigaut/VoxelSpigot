package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public interface BlockChange {

    boolean matchBlock(Location origin, Rotation rotation);

    @NotNull
    Location getLocation(Location origin, Rotation rotation);

    @NotNull
    Block getBlock(Location origin, Rotation rotation);

    void removeBlock(Location origin, Rotation rotation);

    void updateBlock(Location origin, Rotation rotation);

}