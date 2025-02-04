package io.github.pigaut.voxel.meta.placeholder;

import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlaceholderSupplier {

    @NotNull Placeholder[] getPlaceholders();

    PlaceholderSupplier[] EMPTY = new PlaceholderSupplier[0];

    static PlaceholderSupplier of(Placeholder... placeholders) {
        return () -> placeholders;
    }

}
