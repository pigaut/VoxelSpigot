package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public interface BlockChange {

    @NotNull Location getLocation(Location origin, Rotation rotation);

    @NotNull Block getBlock(Location origin, Rotation rotation);

    boolean isPlaced(@NotNull Location origin, @NotNull Rotation rotation);

    void place(@NotNull Location origin, @NotNull Rotation rotation);

    void remove(@NotNull Location origin, @NotNull Rotation rotation);

}