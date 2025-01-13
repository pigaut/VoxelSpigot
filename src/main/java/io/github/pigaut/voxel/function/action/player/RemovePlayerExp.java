package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class RemovePlayerExp implements PlayerAction {

    private final int amount;

    public RemovePlayerExp(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addExp(amount);
    }

}
