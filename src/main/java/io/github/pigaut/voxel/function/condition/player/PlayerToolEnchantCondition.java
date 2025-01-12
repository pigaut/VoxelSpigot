package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolEnchantCondition implements PlayerCondition {

    private final List<Enchantment> enchants;

    public PlayerToolEnchantCondition(Enchantment enchantment) {
        this.enchants = List.of(enchantment);
    }

    public PlayerToolEnchantCondition(List<Enchantment> enchants) {
        this.enchants = enchants;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            for (Enchantment enchant : enchants) {
                if (!meta.hasEnchant(enchant)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static ConfigLoader<PlayerToolEnchantCondition> newConfigLoader() {
        return new ConfigLoader<PlayerToolEnchantCondition>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'has tool enchant'";
            }

            @Override
            public String getKey() {
                return "HAS_TOOL_ENCHANT";
            }

            @Override
            public @NotNull PlayerToolEnchantCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new PlayerToolEnchantCondition(scalar.load(Enchantment.class));
            }

            @Override
            public @NotNull PlayerToolEnchantCondition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new PlayerToolEnchantCondition(sequence.toList(Enchantment.class));
            }
        };
    }

}
