package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class PlayerHasExp implements PlayerCondition {

    private final Amount exp;

    public PlayerHasExp(Amount exp) {
        this.exp = exp;
    }

    @Override
    public Boolean evaluate(@NotNull PlayerState player) {
        return exp.match(player.asPlayer().getTotalExperience());
    }

}
