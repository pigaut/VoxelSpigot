package io.github.pigaut.voxel.core.hologram;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public interface Hologram {

    @Nullable
    HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders);

    @Nullable
    default HologramDisplay spawn(Location location, PlaceholderSupplier... placeholders) {
        return spawn(location, Rotation.NONE, placeholders);
    }

}
