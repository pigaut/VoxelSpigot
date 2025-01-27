package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class PlayerIsSneaking implements PlayerCondition {

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        return player.asPlayer().isSneaking();
    }

}
