package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class EqualsPlaceholderString implements Condition {

    private final String id;
    private final String value;

    public EqualsPlaceholderString(@NotNull String id, @NotNull String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean isMet(@Nullable PluginPlayer player, @Nullable Block block) {
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
