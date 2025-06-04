package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class GiveFlagToPlayer implements PlayerAction {

    private final String flag;

    public GiveFlagToPlayer(String flag) {
        this.flag = flag;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.addFlag(flag);
    }

}
