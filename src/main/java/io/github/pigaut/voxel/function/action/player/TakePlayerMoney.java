package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class TakePlayerMoney implements PlayerAction {

    private final EconomyHook economy;
    private final double amount;

    public TakePlayerMoney(@NotNull EconomyHook economy, double amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        economy.withdrawMoney(player.asPlayer(), amount);
    }

}
