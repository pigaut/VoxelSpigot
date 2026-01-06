package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;

public class ItemUtil {

    public static boolean isAir(@NotNull ItemStack item) {
        return MaterialUtil.isAir(item.getType());
    }

    public static boolean isNotAir(@NotNull ItemStack item) {
        return MaterialUtil.isNotAir(item.getType());
    }

    public static void dropItem(@NotNull Location location, @NotNull ItemStack item) {
        dropItem(location, item, 1, 0);
    }

    public static void dropItem(@NotNull Location location, @NotNull ItemStack item, int amount) {
        dropItem(location, item, amount, 0);
    }

    public static void dropItem(@NotNull Location location, @NotNull ItemStack item, int amount, int fortuneLevel) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        ItemStack drop = item.clone();
        drop.setAmount(Fortune.getDropAmount(amount, fortuneLevel));
        world.dropItemNaturally(location, drop);
    }

    public static void dropItems(@NotNull Location location, @NotNull Collection<ItemStack> items) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        for (ItemStack drop : items) {
            world.dropItemNaturally(location, drop);
        }
    }

    public static void damagePlayerTool(@NotNull Player player, int amount) {
        damageItem(player.getInventory().getItemInMainHand(), player, amount);
    }

    public static void damageItem(@NotNull ItemStack item, @NotNull Player player, int amount) {
        Preconditions.checkArgument(amount >= 0, "Damage amount must be positive");
        Material material = item.getType();
        if (!material.isItem()) {
            return;
        }

        Damageable damageable = (Damageable) item.getItemMeta();
        if (damageable == null || damageable.isUnbreakable()) {
            return;
        }

        int maxDamage = damageable.hasMaxDamage() ? damageable.getMaxDamage() : material.getMaxDurability();
        if (maxDamage < 1) {
            return;
        }

        int damageAmount = amount;
        if (damageable.hasEnchant(Enchantment.UNBREAKING)) {
            int unbreakingLevel = damageable.getEnchantLevel(Enchantment.UNBREAKING);
            damageAmount = 0;
            Random random = ThreadLocalRandom.current();
            for (int i = 0; i < amount; i++) {
                if (random.nextInt(unbreakingLevel + 1) == 0) {
                    damageAmount++;
                }
            }
        }

        int damageToApply = (damageable.hasDamage() ? damageable.getDamage() : 0) + damageAmount;
        if (damageToApply >= maxDamage) {
            item.setAmount(0);
            player.playSound(player.getLocation(), "entity.item.break", 1f, 1f);
        }
        else {
            damageable.setDamage(damageToApply);
            item.setItemMeta(damageable);
        }
    }

}
