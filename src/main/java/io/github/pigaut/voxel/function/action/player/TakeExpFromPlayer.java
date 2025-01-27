package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class TakeExpFromPlayer implements PlayerAction {

    private final Amount amount;

    public TakeExpFromPlayer(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addExp(-amount.getInt());
    }

}
