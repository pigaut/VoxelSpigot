package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class PlayerHasFlag implements PlayerCondition {

    private final String flag;

    public PlayerHasFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        return player.hasFlag(flag);
    }

}
