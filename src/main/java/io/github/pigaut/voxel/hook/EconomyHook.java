package io.github.pigaut.voxel.hook;

import io.github.pigaut.voxel.server.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

public class EconomyHook extends PluginHook {

    private final Economy economy;

    private EconomyHook(@NotNull Plugin plugin, @NotNull Economy economy) {
        super(plugin);
        this.economy = economy;
    }

    public static @Nullable EconomyHook newInstance() {
        final Plugin plugin = SpigotServer.getPlugin("Vault");
        if (plugin == null) {
            return null;
        }
        final Economy economy = SpigotServer.getRegisteredService(Economy.class);
        if (economy == null) {
            return null;
        }
        return new EconomyHook(plugin, economy);
    }

    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    public EconomyResponse depositMoney(OfflinePlayer player, double amount) {
        return economy.depositPlayer(player, amount);
    }

    public EconomyResponse withdrawMoney(OfflinePlayer player, double amount) {
        return economy.withdrawPlayer(player, amount);
    }

}
