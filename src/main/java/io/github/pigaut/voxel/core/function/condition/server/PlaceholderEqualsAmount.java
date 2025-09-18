package io.github.pigaut.voxel.core.function.condition.server;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PlaceholderEqualsAmount implements Condition {

    private final String id;
    private final Amount amount;

    public PlaceholderEqualsAmount(String id, Amount amount) {
        this.id = id;
        this.amount = amount;
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

        final Double amount = ParseUtil.parseDoubleOrNull(parsedValue);
        if (amount != null) {
            return this.amount.match(amount);
        }

        return false;
    }

}
