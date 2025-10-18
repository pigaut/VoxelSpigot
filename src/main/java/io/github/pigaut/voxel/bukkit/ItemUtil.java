package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class ItemUtil {

    @NotNull
    public static Item dropItem(@NotNull Location location, @NotNull ItemStack item) {
        return dropItem(location, item, 1, 0);
    }

    @NotNull
    public static Item dropItem(@NotNull Location location, @NotNull ItemStack item, int amount) {
        return dropItem(location, item, amount, 0);
    }

    @NotNull
    public static Item dropItem(@NotNull Location location, @NotNull ItemStack item, int amount, int fortuneLevel) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        ItemStack drop = item.clone();
        drop.setAmount(Fortune.getDropAmount(amount, fortuneLevel));
        return world.dropItemNaturally(location, drop);
    }

    public static void damageItem(@NotNull ItemStack item, @NotNull Player player, int amount) {
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return;
        }

        if (meta.isUnbreakable()) {
            return;
        }

        int unbreaking = item.getEnchantmentLevel(Enchantment.UNBREAKING);
        int actualDamage = 0;

        for (int i = 0; i < amount; i++) {
            if (unbreaking <= 0 || ThreadLocalRandom.current().nextInt(unbreaking + 1) == 0) {
                actualDamage++;
            }
        }

        damageable.setDamage(damageable.getDamage() + actualDamage);
        item.setItemMeta(meta);

        if (damageable.getDamage() >= item.getType().getMaxDurability()) {
            item.setAmount(0);
            player.playSound(player.getLocation(), "entity.item.break", 1f, 1f);
        }
    }

}
