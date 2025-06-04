package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class TakeFlagFromPlayer implements PlayerAction {

    private final String flag;

    public TakeFlagFromPlayer(String flag) {
        this.flag = flag;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.removeFlag(flag);
    }

}
