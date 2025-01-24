package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class PlayerHasMoney implements PlayerCondition {

    private final EconomyHook economy;
    private final double amount;

    public PlayerHasMoney(@NotNull EconomyHook economy, double amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        return economy.getBalance(player.asPlayer()) >= amount;
    }

}
