package io.github.pigaut.voxel.config.persistent.data;

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

    public static @Nullable PersistentDataType<?, ?> getDataType(@NotNull PersistentDataContainer container, @NotNull NamespacedKey key) {
        if (container.has(key, PersistentDataType.STRING)) {
            return PersistentDataType.STRING;
        } else if (container.has(key, PersistentDataType.BYTE)) {
            return PersistentDataType.BYTE;
        } else if (container.has(key, PersistentDataType.SHORT)) {
            return PersistentDataType.SHORT;
        } else if (container.has(key, PersistentDataType.INTEGER)) {
            return PersistentDataType.INTEGER;
        } else if (container.has(key, PersistentDataType.LONG)) {
            return PersistentDataType.LONG;
        } else if (container.has(key, PersistentDataType.FLOAT)) {
            return PersistentDataType.FLOAT;
        } else if (container.has(key, PersistentDataType.DOUBLE)) {
            return PersistentDataType.DOUBLE;
        } else if (container.has(key, PersistentDataType.BYTE_ARRAY)) {
            return PersistentDataType.BYTE_ARRAY;
        } else if (container.has(key, PersistentDataType.INTEGER_ARRAY)) {
            return PersistentDataType.INTEGER_ARRAY;
        } else if (container.has(key, PersistentDataType.LONG_ARRAY)) {
            return PersistentDataType.LONG_ARRAY;
        } else if (container.has(key, PersistentDataType.TAG_CONTAINER)) {
            return PersistentDataType.TAG_CONTAINER;
        } else if (container.has(key, PersistentDataType.TAG_CONTAINER_ARRAY)) {
            return PersistentDataType.TAG_CONTAINER_ARRAY;
        }

        return null;
    }

    public static @Nullable PersistentDataType<?, ?> getDataType(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return getDataType(holder.getPersistentDataContainer(), key);
    }

}
