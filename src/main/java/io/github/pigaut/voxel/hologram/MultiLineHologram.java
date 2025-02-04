package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiLineHologram implements Hologram {

    private final List<Hologram> holograms;

    public MultiLineHologram(@NotNull List<@NotNull Hologram> holograms) {
        this.holograms = holograms;
    }

    @Override
    public HologramDisplay spawn(Location location, Rotation rotation, boolean persistent, PlaceholderSupplier... placeholderSuppliers) {
        final HologramDisplay hologram = new MultiLineHologramDisplay(location, rotation, persistent, placeholderSuppliers);
        hologram.spawn();
        return hologram;
    }

    private class MultiLineHologramDisplay implements HologramDisplay {

        private final List<HologramDisplay> displays;

        protected MultiLineHologramDisplay(Location location, Rotation rotation, boolean persistent, PlaceholderSupplier... placeholderSuppliers) {
            this.displays = holograms.stream()
                    .map(hologram -> hologram.spawn(location, rotation, persistent, placeholderSuppliers))
                    .filter(Objects::nonNull)
                    .toList();
        }

        @Override
        public boolean exists() {
            for (HologramDisplay display: displays) {
                if (display.exists()) return true;
            }
            return false;
        }

        @Override
        public void spawn() {
            for (HologramDisplay display: displays) {
                display.spawn();
            }
        }

        @Override
        public void despawn() {
            for (HologramDisplay display: displays) {
                display.despawn();
            }
        }

    }

}
