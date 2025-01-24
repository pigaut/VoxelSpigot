package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public interface Hologram {

    @Nullable
    HologramDisplay spawn(Location location, boolean persistent, PlaceholderSupplier... placeholderSuppliers);

}
