package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class GiveFlagToPlayer implements PlayerAction {

    private final Flag flag;

    public GiveFlagToPlayer(Flag flag) {
        this.flag = flag;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addFlag(flag);
    }

}
