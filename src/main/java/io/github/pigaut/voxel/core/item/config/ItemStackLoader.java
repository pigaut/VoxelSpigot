package io.github.pigaut.voxel.core.item.config;

import com.cryptomorin.xseries.*;
import de.tr7zw.changeme.nbtapi.*;
import de.tr7zw.changeme.nbtapi.iface.*;
import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.config.deserializer.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.*;
import org.jetbrains.annotations.*;

import java.util.*;

/*
 / ItemStack config loader for 1.8.8+
*/
public class ItemStackLoader implements SectionLoader<ItemStack> {

    private final EnchantmentDeserializer enchantDeserializer = new EnchantmentDeserializer();

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid item";
    }

    @Override
    public @NotNull ItemStack loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {
        final Material type = config.get("type|material", Material.class);
        final int amount = config.getOptionalInteger("amount").orElse(1);
        final ItemStack item = new ItemStack(type, amount);

        {
            final ItemMeta meta = item.getItemMeta();

            final Optional<String> nameField = config.getOptionalString("name|display", StringColor.FORMATTER);
            nameField.ifPresent(meta::setDisplayName);

            final Optional<Integer> repairCostField = config.getOptionalInteger("repair-cost");
            repairCostField.ifPresent(repairCost -> {
                if (meta instanceof Repairable repairable) {
                    repairable.setRepairCost(repairCost);
                }
            });

            final List<String> lore = config.getStringList("lore", StringColor.FORMATTER);
            if (!lore.isEmpty()) {
                meta.setLore(lore);
            }

            final List<ItemFlag> itemFlags = config.getList("flags", ItemFlag.class);
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));

            final boolean shouldGlow = config.getOptionalBoolean("glow").orElse(false);
            if (shouldGlow) {
                meta.addEnchant(XEnchantment.LUCK_OF_THE_SEA.get(), 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            final Optional<Integer> optionalModelData = config.getOptionalInteger("model-data|custom-model-data");
            optionalModelData.ifPresent(meta::setCustomModelData);

            final Optional<Boolean> optionalUnbreakable = config.getOptionalBoolean("unbreakable");
            optionalUnbreakable.ifPresent(meta::setUnbreakable);

            final Optional<Integer> optionalDurability = config.getOptionalInteger("damage|durability");
            optionalDurability.ifPresent(damage -> {
                if (meta instanceof Damageable damageable) {
                    damageable.setDamage(10);
                }
                else {
                    throw new InvalidConfigurationException(config, "damage", "Current item type does not support durability");
                }
            });

            final ConfigSection enchantsConfig = config.getSectionOrCreate("enchantments|enchants");
            for (String key : enchantsConfig.getKeys()) {
                try {
                    meta.addEnchant(enchantDeserializer.deserialize(key), enchantsConfig.getInteger(key), true);
                } catch (DeserializationException e) {
                    throw new InvalidConfigurationException(config, "enchantments", e.getMessage());
                }
            }

            final PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            for (ConfigSection dataConfig : config.getNestedSections("persistent-data")) {
                final String key = dataConfig.getString("key");
                final NamespacedKey namespacedKey = NamespacedKey.fromString(key);
                if (namespacedKey == null) {
                    throw new InvalidConfigurationException(dataConfig, "Could not create namespaced key from: '" + key + "'");
                }

                final String dataType = dataConfig.getString("type", StringStyle.SNAKE);
                switch (dataType) {
                    case "byte" -> dataContainer.set(namespacedKey, PersistentDataType.BYTE, (byte) dataConfig.getInteger("value"));
                    case "short" -> dataContainer.set(namespacedKey, PersistentDataType.SHORT, (short) dataConfig.getInteger("value"));
                    case "integer" -> dataContainer.set(namespacedKey, PersistentDataType.INTEGER, dataConfig.getInteger("value"));
                    case "long" -> dataContainer.set(namespacedKey, PersistentDataType.LONG, dataConfig.getLong("value"));
                    case "float" -> dataContainer.set(namespacedKey, PersistentDataType.FLOAT, (float) dataConfig.getDouble("value"));
                    case "double" -> dataContainer.set(namespacedKey, PersistentDataType.DOUBLE, dataConfig.getDouble("value"));
                    case "string" -> dataContainer.set(namespacedKey, PersistentDataType.STRING, dataConfig.getString("value"));
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

            item.setItemMeta(meta);
        }

        final Optional<String> optionalHeadTexture = config.getOptionalString("head-texture|head-data|head");
        optionalHeadTexture.ifPresent(texture -> HeadTexture.apply(item, texture));

        NBT.modify(item, itemNBT -> {
            final ReadWriteNBTCompoundList attributeCompounds = itemNBT.getCompoundList("AttributeModifiers");
            for (Attribute attribute : config.getAll("attributes", Attribute.class)) {
                final ReadWriteNBT attributeCompound = attributeCompounds.addCompound();
                attributeCompound.setString("AttributeName", attribute.getAttributeType());
                attributeCompound.setString("Name", attribute.getName());
                attributeCompound.setDouble("Amount", attribute.getAmount());
                attributeCompound.setString("Slot", attribute.getSlot());
                attributeCompound.setInteger("Operation", attribute.getOperation());
                final UUID uuid = UUID.randomUUID();
                attributeCompound.setLong("UUIDLeast", uuid.getLeastSignificantBits());
                attributeCompound.setLong("UUIDMost", uuid.getMostSignificantBits());
            }
        });

        return item;
    }

}
