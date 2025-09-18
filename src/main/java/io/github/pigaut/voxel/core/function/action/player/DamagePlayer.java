package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class DamagePlayer implements PlayerAction {

    private final Amount amount;

    public DamagePlayer(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.heal(-amount.getInteger());
    }

}
