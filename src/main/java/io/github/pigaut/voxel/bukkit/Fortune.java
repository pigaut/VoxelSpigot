package io.github.pigaut.voxel.bukkit;

import com.cryptomorin.xseries.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;

import java.util.concurrent.*;

public class Fortune {

    public static int getDropAmount(int fortuneLevel) {
        return getDropAmount(1, fortuneLevel);
    }

    public static int getDropAmount(int baseAmount, int fortuneLevel) {
        if (fortuneLevel <= 0) {
            return baseAmount;
        }

        int bonus = ThreadLocalRandom.current().nextInt(fortuneLevel + 2) - 1;
        if (bonus < 0) {
            bonus = 0;
        }

        return baseAmount * (1 + bonus);
    }

    private static final Enchantment FORTUNE_ENCHANT = XEnchantment.FORTUNE.get();

    public static int getEnchantLevel(ItemStack tool) {
        if (FORTUNE_ENCHANT == null) {
            return 0;
        }
        return tool.getEnchantmentLevel(FORTUNE_ENCHANT);
    }

}
