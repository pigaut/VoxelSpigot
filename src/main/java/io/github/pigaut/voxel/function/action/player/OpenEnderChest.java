package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class OpenEnderChest implements PlayerAction {

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.openEnderChest();
    }

}
