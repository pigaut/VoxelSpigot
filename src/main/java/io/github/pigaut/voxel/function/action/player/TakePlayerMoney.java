package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class TakePlayerMoney implements PlayerAction {

    private final EnhancedPlugin plugin;
    private final EconomyHook economy;
    private final double amount;

    public TakePlayerMoney(EnhancedPlugin plugin, EconomyHook economy, double amount) {
        this.plugin = plugin;
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        if (!economy.isEnabled()) {
            plugin.getLogger().severe("Could not withdraw money from player because Vault has been disabled!");
            return;
        }
        economy.withdrawMoney(player.asPlayer(), amount);
    }

}
