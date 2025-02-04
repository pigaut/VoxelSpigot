package io.github.pigaut.voxel.util;

import org.bukkit.*;
import org.bukkit.persistence.*;
import org.jetbrains.annotations.*;

public class PersistentData {

    private PersistentData() {}

    public static boolean hasString(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return holder.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static String getString(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return holder.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public static void setString(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key, @NotNull String value) {
        holder.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
    }

}
