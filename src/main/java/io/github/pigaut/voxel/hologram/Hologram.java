package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public interface Hologram {

    @Nullable
    HologramDisplay spawn(Location location, Rotation rotation, boolean persistent, PlaceholderSupplier... placeholderSuppliers);

    @Nullable
    default HologramDisplay spawn(Location location, boolean persistent, PlaceholderSupplier... placeholderSuppliers) {
        return spawn(location, Rotation.NONE, persistent, placeholderSuppliers);
    }

}
