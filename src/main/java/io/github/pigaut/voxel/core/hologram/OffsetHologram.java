package io.github.pigaut.voxel.core.hologram;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;

public class OffsetHologram implements Hologram {

    private final Hologram hologram;
    public final double offsetX, offsetY, offsetZ;

    public OffsetHologram(Hologram hologram, double offsetX, double offsetY, double offsetZ) {
        this.hologram = hologram;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final Location offsetLocation = rotation.apply(location.clone(), offsetX, offsetY, offsetZ);
        return hologram.spawn(offsetLocation, placeholders);
    }

}
