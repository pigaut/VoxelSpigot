package io.github.pigaut.voxel.hook;

import io.github.pigaut.voxel.server.SpigotServer;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class EconomyHook {

    private final Economy economy;

    private EconomyHook(@NotNull Economy economy) {
        this.economy = economy;
    }

    public static EconomyHook newInstance() {
        final Economy economy = SpigotServer.getRegisteredService(Economy.class);
        if (economy == null) {
            return null;
        }
        return new EconomyHook(economy);
    }

    public Economy getEconomy() {
        return economy;
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
