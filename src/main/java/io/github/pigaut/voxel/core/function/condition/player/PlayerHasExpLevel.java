package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class PlayerHasExpLevel implements PlayerCondition {

    private final Amount level;

    public PlayerHasExpLevel(Amount level) {
        this.level = level;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        return level.match(player.asPlayer().getLevel());
    }

}
