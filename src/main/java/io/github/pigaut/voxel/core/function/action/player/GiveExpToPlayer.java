package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class GiveExpToPlayer implements PlayerAction {

    private final Amount amount;

    public GiveExpToPlayer(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.addExp(amount.getInt());
    }

}
