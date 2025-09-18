package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class PlayerHasMoney implements PlayerCondition {

    private final EconomyHook economy;
    private final Amount amount;

    public PlayerHasMoney(@NotNull EconomyHook economy, Amount amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        return amount.match(economy.getBalance(player.asPlayer()));
    }

}
