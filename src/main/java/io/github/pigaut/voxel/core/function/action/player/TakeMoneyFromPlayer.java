package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class TakeMoneyFromPlayer implements PlayerAction {

    private final EconomyHook economy;
    private final Amount amount;

    public TakeMoneyFromPlayer(@NotNull EconomyHook economy, Amount amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        economy.withdrawMoney(player.asPlayer(), amount.getDouble());
    }

}
