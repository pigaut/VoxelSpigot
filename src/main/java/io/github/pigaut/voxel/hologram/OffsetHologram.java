package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.*;

public class OffsetHologram implements Hologram {

    private final Hologram hologram;
    private final double offsetX, offsetY, offsetZ;

    public OffsetHologram(Hologram hologram, double offsetX, double offsetY, double offsetZ) {
        this.hologram = hologram;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public HologramDisplay spawn(Location location, boolean persistent, PlaceholderSupplier... placeholderSuppliers) {
        return hologram.spawn(location.clone().add(offsetX, offsetY, offsetZ), persistent, placeholderSuppliers);
    }

}
