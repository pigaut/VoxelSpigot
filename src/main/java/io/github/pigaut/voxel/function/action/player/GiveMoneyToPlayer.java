package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class GiveMoneyToPlayer implements PlayerAction {

    private final EconomyHook economy;
    private final Amount amount;

    public GiveMoneyToPlayer(@NotNull EconomyHook economy, Amount amount) {
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        economy.depositMoney(player.asPlayer(), amount.getDouble());
    }

}
