package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class HealPlayer implements PlayerAction {

    private final Amount amount;

    public HealPlayer(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.heal(amount.getInt());
    }

}
