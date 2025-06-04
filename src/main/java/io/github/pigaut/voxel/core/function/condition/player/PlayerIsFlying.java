package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class PlayerIsFlying implements PlayerCondition {

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        return player.asPlayer().isFlying();
    }

}
