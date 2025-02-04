package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class EqualsPlaceholderAmount implements Condition {

    private final String id;
    private final Amount amount;

    public EqualsPlaceholderAmount(String id, Amount amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public boolean isMet(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        final String parsedValue;
        if (player != null) {
            parsedValue = StringPlaceholders.parseAll(player.asPlayer(), id, player.getPlaceholders());
        }
        else {
            parsedValue = StringPlaceholders.parseAll(id);
        }
        final Double amount = Deserializers.getDouble(parsedValue);

        if (amount != null) {
            return this.amount.match(amount);
        }
        return false;
    }

}
