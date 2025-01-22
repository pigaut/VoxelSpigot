package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class PlayerHasMoney implements PlayerCondition {

    private final EnhancedPlugin plugin;
    private final EconomyHook economy;
    private final double amount;

    public PlayerHasMoney(@NotNull EnhancedPlugin plugin, @NotNull EconomyHook economy, double amount) {
        this.plugin = plugin;
        this.economy = economy;
        this.amount = amount;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        if (!economy.isEnabled()) {
            plugin.getLogger().severe("Could not check player money because Vault has been disabled!");
            return false;
        }
        return economy.getBalance(player.asPlayer()) >= amount;
    }

}
