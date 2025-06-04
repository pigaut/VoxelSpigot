package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class CloseInventory implements PlayerAction {

    @Override
    public void execute(@NotNull PlayerState player) {
        player.closeInventory();
    }

}
