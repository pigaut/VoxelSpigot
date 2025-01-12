package io.github.pigaut.voxel.meta.placeholder;

import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlaceholderSupplier {

    PlaceholderSupplier[] EMPTY = new PlaceholderSupplier[0];

    @NotNull Placeholder[] getPlaceholders();

}
