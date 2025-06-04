package io.github.pigaut.voxel.core.function.condition.server;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class EqualsPlaceholderString implements Condition {

    private final String id;
    private final String value;

    public EqualsPlaceholderString(@NotNull String id, @NotNull String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean isMet(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        final String parsedValue;
        if (player != null) {
            parsedValue = StringPlaceholders.parseAll(player.asPlayer(), id, player.getPlaceholders());
        }
        else {
            parsedValue = StringPlaceholders.parseAll(id);
        }

        return value.equalsIgnoreCase(parsedValue);
    }

}
