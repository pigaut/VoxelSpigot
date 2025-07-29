package io.github.pigaut.voxel.core.item.config;

import com.cryptomorin.xseries.*;
import com.cryptomorin.xseries.profiles.builder.*;
import com.cryptomorin.xseries.profiles.objects.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import de.tr7zw.changeme.nbtapi.*;
import de.tr7zw.changeme.nbtapi.iface.*;
import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.mapper.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.*;

import java.util.*;

/*
 / ItemStack config mapper
*/
public class ItemStackMapper implements SectionMapper<ItemStack> {

    @Override
    public void mapSection(ConfigSection section, ItemStack item) {
        Material type = item.getType();
        section.set("type", type);
        int amount = item.getAmount();
        if (amount != 1) {
            section.set("amount", item.getAmount());
        }

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName()) {
                section.set("name", meta.getDisplayName());
            }

            if (meta.hasLore()) {
                section.set("lore", meta.getLore());
            }

            if (meta instanceof Damageable damageable && damageable.hasDamage()) {
                section.set("damage", damageable.getDamage());
            }

            if (meta.isUnbreakable()) {
                section.set("unbreakable", true);
            }

            if (meta.hasCustomModelData()) {
                section.set("model-data", meta.getCustomModelData());
            }

            if (meta instanceof Repairable repairable) {
                if (repairable.hasRepairCost()) {
                    section.set("repair-cost", repairable.getRepairCost());
                }
            }

            Set<ItemFlag> itemFlags = meta.getItemFlags();
            if (!itemFlags.isEmpty()) {
                section.set("flags", itemFlags);
            }

            final ConfigSection enchantsConfig = section.getSectionOrCreate("enchants");
            meta.getEnchants().forEach((enchant, level) -> {
                enchantsConfig.set(XEnchantment.of(enchant).name(), level);
            });

            final PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            if (!dataContainer.isEmpty()) {
                section.set("persistent-data", dataContainer);
            }

            final String headTexture = XSkull.of(meta).getProfileValue();
            if (headTexture != null) {
                section.set("head-texture|head", headTexture);
            }
        }

        NBT.get(item, nbt -> {
            mapAttributes(section, nbt);
        });

    }

    protected void mapAttributes(ConfigSection section, ReadableNBT itemNBT) {
        final ReadableNBTList<ReadWriteNBT> attributesCompound = itemNBT.getCompoundList("AttributeModifiers");
        final List<Attribute> foundAttributes = new ArrayList<>();
        if (attributesCompound != null) {
            for (ReadWriteNBT attributeCompound : attributesCompound) {
                final String attribute = attributeCompound.getString("AttributeName")
                        .replace("minecraft:", "");
                final String name = attributeCompound.getString("Name");
                final double amount = attributeCompound.getDouble("Amount");
                final String slot = attributeCompound.getString("Slot");
                final int operation = attributeCompound.getInteger("Operation");

                foundAttributes.add(new Attribute(attribute, name, amount, slot, operation));
            }
        }

        if (!foundAttributes.isEmpty()) {
            section.set("attributes", foundAttributes);
        }
    }

}