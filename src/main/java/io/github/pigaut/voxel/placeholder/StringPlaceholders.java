package io.github.pigaut.voxel.placeholder;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StringPlaceholders {

    public static String parseAll(@NotNull String text, @NotNull PlaceholderSupplier... suppliers) {
        return parseAll(null, text, Arrays.asList(suppliers));
    }

    public static String parseAll(@Nullable OfflinePlayer player, @NotNull String text) {
        return parseAll(player, text, List.of(PlaceholderSupplier.EMPTY));
    }

    public static String parseAll(@Nullable OfflinePlayer player, @NotNull String text, @NotNull PlaceholderSupplier... suppliers) {
        return parseAll(player, text, Arrays.asList(suppliers));
    }

    public static String parseAll(@NotNull String text, @NotNull Collection<? extends PlaceholderSupplier> suppliers) {
        return parseAll(null, text, suppliers);
    }

    public static String parseAll(@Nullable OfflinePlayer player, @NotNull String text, @NotNull Collection<? extends PlaceholderSupplier> suppliers) {
        PlaceholdersHook placeholderAPI = SpigotServer.getPlaceholderAPIHook();
        if (placeholderAPI != null) {
            text = placeholderAPI.setPlaceholders(player, text);
        }

        for (PlaceholderSupplier supplier : suppliers) {
            for (Placeholder placeholder : supplier.getPlaceholders()) {
                text = placeholder.format(text);
            }
        }
        return text;
    }

}
