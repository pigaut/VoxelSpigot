package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.placeholder.*;
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
    public HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final HologramDisplay hologram = new MultiLineHologramDisplay(location, rotation, placeholders);
        return hologram;
    }

    private class MultiLineHologramDisplay implements HologramDisplay {

        private final List<HologramDisplay> displays;

        protected MultiLineHologramDisplay(Location location, Rotation rotation, PlaceholderSupplier[] placeholderSuppliers) {
            displays = holograms.stream()
                    .map(hologram -> hologram.spawn(location, rotation, placeholderSuppliers))
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
        public void update() {
            for (HologramDisplay display : displays) {
                display.update();
            }
        }

        @Override
        public void destroy() {
            for (HologramDisplay display: displays) {
                display.destroy();
            }
        }

    }

}
