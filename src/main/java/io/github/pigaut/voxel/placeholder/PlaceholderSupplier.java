package io.github.pigaut.voxel.placeholder;

import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlaceholderSupplier {

    @NotNull Placeholder[] getPlaceholders();

    PlaceholderSupplier[] EMPTY = new PlaceholderSupplier[0];

    static PlaceholderSupplier of(Placeholder... placeholders) {
        return () -> placeholders;
    }

    static PlaceholderSupplier of(String id, Object value) {
        return () -> new Placeholder[]{ Placeholder.of(id, value) };
    }

}
