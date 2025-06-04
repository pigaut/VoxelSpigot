package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public interface Hologram {

    @Nullable
    HologramDisplay spawn(Location location, Rotation rotation, boolean persistent, PlaceholderSupplier... placeholders);

    @Nullable
    default HologramDisplay spawn(Location location, boolean persistent, PlaceholderSupplier... placeholders) {
        return spawn(location, Rotation.NONE, persistent, placeholders);
    }

}
