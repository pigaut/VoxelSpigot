package io.github.pigaut.voxel.core.item.config;

import com.cryptomorin.xseries.*;
import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.config.deserializer.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ItemStackLoader implements ConfigLoader.Section<ItemStack> {

    private static final EnchantmentDeserializer enchantDeserializer = new EnchantmentDeserializer();

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid item";
    }

    @Override
    public @NotNull ItemStack loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Material type = section.getRequired("type|material", Material.class);
        final int amount = section.getInteger("amount").withDefault(1);
        final ItemStack item = new ItemStack(type, amount);

        {
            ItemMeta meta = item.getItemMeta();

            section.getString("name|display", StringColor.FORMATTER)
                    .ifPresent(meta::setDisplayName);

            List<String> lore = section.getStringList("lore", StringColor.FORMATTER);
            if (!lore.isEmpty()) {
                meta.setLore(lore);
            }

            List<ItemFlag> itemFlags = section.getAll("flags", ItemFlag.class);
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));

            final boolean shouldGlow = section.getBoolean("glow").withDefault(false);
            if (shouldGlow) {
                meta.addEnchant(XEnchantment.LUCK_OF_THE_SEA.get(), 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            section.getInteger("model-data|custom-model-data")
                    .ifPresent(meta::setCustomModelData);

            section.getBoolean("unbreakable")
                    .ifPresent(meta::setUnbreakable);

            section.getInteger("repair-cost").ifPresent(repairCost -> {
                if (!(meta instanceof Repairable repairable)) {
                    throw new InvalidConfigurationException(section, "repair-cost", "Current item type cannot be repaired");
                }
                repairable.setRepairCost(repairCost);
            });

            section.getInteger("damage|durability").ifPresent(damage -> {
                if (!(meta instanceof Damageable damageable)) {
                    throw new InvalidConfigurationException(section, "damage", "Current item type cannot be damaged");
                }
                damageable.setDamage(10);
            });

            final ConfigSection enchantsSection = section.getSectionOrCreate("enchantments|enchants");
            for (String key : enchantsSection.getKeys()) {
                Enchantment enchantment = enchantDeserializer.loadFromKey(enchantsSection, key);
                int level = enchantsSection.getRequiredInteger(key);
                meta.addEnchant(enchantment, level, true);
            }

            final PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            for (ConfigSection dataConfig : section.getNestedSections("persistent-data")) {
                final String key = dataConfig.getRequiredString("key");
                final NamespacedKey namespacedKey = NamespacedKey.fromString(key);
                if (namespacedKey == null) {
                    throw new InvalidConfigurationException(dataConfig, "Could not create namespaced key from: '" + key + "'");
                }

                final String dataType = dataConfig.getRequiredString("type", CaseStyle.SNAKE);
                switch (dataType) {
                    case "byte" -> dataContainer.set(namespacedKey, PersistentDataType.BYTE, dataConfig.getRequiredInteger("value").byteValue());
                    case "short" -> dataContainer.set(namespacedKey, PersistentDataType.SHORT, dataConfig.getRequiredInteger("value").shortValue());
                    case "integer" -> dataContainer.set(namespacedKey, PersistentDataType.INTEGER, dataConfig.getRequiredInteger("value"));
                    case "long" -> dataContainer.set(namespacedKey, PersistentDataType.LONG, dataConfig.getRequiredLong("value"));
                    case "float" -> dataContainer.set(namespacedKey, PersistentDataType.FLOAT, dataConfig.getRequiredFloat("value"));
                    case "double" -> dataContainer.set(namespacedKey, PersistentDataType.DOUBLE, dataConfig.getRequiredDouble("value"));
                    case "string" -> dataContainer.set(namespacedKey, PersistentDataType.STRING, dataConfig.getRequiredString("value"));
                    case "byte_array" -> {
                        List<Integer> intList = dataConfig.getIntegerList("value");
                        byte[] byteArray = new byte[intList.size()];
                        for (int i = 0; i < intList.size(); i++) {
                            byteArray[i] = (byte) (int) intList.get(i);
                        }
                        dataContainer.set(namespacedKey, PersistentDataType.BYTE_ARRAY, byteArray);
                    }
                    case "int_array" -> dataContainer.set(namespacedKey, PersistentDataType.INTEGER_ARRAY, dataConfig.getIntegerList("value").stream()
                            .mapToInt(Integer::intValue).toArray());
                    case "long_array" -> dataContainer.set(namespacedKey, PersistentDataType.LONG_ARRAY, dataConfig.getLongList("value").stream()
                            .mapToLong(Long::longValue).toArray());
                    case "tag_container" -> {
                        PersistentDataContainer nestedContainer = meta.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
                        dataContainer.set(namespacedKey, PersistentDataType.TAG_CONTAINER, nestedContainer);
                    }
                    default -> throw new InvalidConfigurationException(dataConfig, "Could not find datatype with name: '" + dataType + "'");
                }
            }

            for (ItemAttribute itemAttribute : section.getAll("attributes", ItemAttribute.class)) {
                meta.addAttributeModifier(itemAttribute.attribute(), itemAttribute.modifier());
            }

            item.setItemMeta(meta);
        }

        section.getString("head-texture|head-data|head").ifPresent(texture ->
                HeadTexture.apply(item, texture));

        return item;
    }

}
