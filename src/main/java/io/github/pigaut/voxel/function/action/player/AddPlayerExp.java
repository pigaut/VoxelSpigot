package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class AddPlayerExp implements PlayerAction {

    private final int amount;

    public AddPlayerExp(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addExp(amount);
    }

}
