package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class PlayerHasExp implements PlayerCondition {

    private final Amount exp;

    public PlayerHasExp(Amount exp) {
        this.exp = exp;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        return exp.match(player.asPlayer().getTotalExperience());
    }

}
