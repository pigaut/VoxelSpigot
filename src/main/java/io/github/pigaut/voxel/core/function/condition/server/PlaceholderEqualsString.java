package io.github.pigaut.voxel.core.function.condition.server;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PlaceholderEqualsString implements Condition {

    private final String id;
    private final String value;
    private final boolean ignoreCase;

    public PlaceholderEqualsString(@NotNull String id, @NotNull String value, boolean ignoreCase) {
        this.id = id;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    @Override
    public Boolean evaluate(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        final String parsedValue;
        if (player != null) {
            parsedValue = StringPlaceholders.parseAll(player.asPlayer(), id, player.getPlaceholderSuppliers());
        }
        else {
            parsedValue = StringPlaceholders.parseAll(id);
        }

        if (ignoreCase) {
            return value.equalsIgnoreCase(parsedValue);
        }
        else {
            return value.equals(parsedValue);
        }
    }

}
