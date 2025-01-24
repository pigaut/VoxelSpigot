package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class GivePlayerMoney implements PlayerAction {

    private final EconomyHook economy;
    private final double amount;

    public GivePlayerMoney(@NotNull EconomyHook economy, double amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        economy.depositMoney(player.asPlayer(), amount);
    }

}
