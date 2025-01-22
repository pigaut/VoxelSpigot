package io.github.pigaut.voxel.meta.placeholder;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class StringPlaceholders {

    public static String parseAll(@NotNull String text, @NotNull PlaceholderSupplier... placeholderSuppliers) {
        return parseAll(null, text, placeholderSuppliers);
    }

    public static String parseAll(@Nullable OfflinePlayer player, @NotNull String text) {
        return parseAll(player, text, PlaceholderSupplier.EMPTY);
    }

    public static String parseAll(@Nullable OfflinePlayer player, @NotNull String text,
                                  @NotNull PlaceholderSupplier... placeholderSuppliers) {
        final PlaceholderAPIHook placeholderAPI = SpigotServer.getPlaceholderAPIHook();
        if (placeholderAPI != null) {
            text = placeholderAPI.setPlaceholders(player, text);
        }

        for (PlaceholderSupplier supplier : placeholderSuppliers) {
            for (Placeholder placeholder : supplier.getPlaceholders()) {
                text = placeholder.format(text);
            }
        }
        return text;
    }

}
