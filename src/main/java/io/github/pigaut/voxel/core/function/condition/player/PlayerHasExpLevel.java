package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class PlayerHasExpLevel implements PlayerCondition {

    private final Amount level;

    public PlayerHasExpLevel(Amount level) {
        this.level = level;
    }

    @Override
    public Boolean evaluate(@NotNull PlayerState player) {
        return level.match(player.asPlayer().getLevel());
    }

}
