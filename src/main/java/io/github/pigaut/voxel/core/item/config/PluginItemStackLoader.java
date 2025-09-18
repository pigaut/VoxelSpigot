package io.github.pigaut.voxel.core.item.config;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PluginItemStackLoader extends ItemStackLoader {

    private final EnhancedPlugin plugin;

    public PluginItemStackLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull ItemStack loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String[] itemData = scalar.toString().split(":");
        final String itemName = itemData[0];
        final Integer amount;
        try {
            amount = itemData.length == 2 ? ParseUtil.parseInteger(itemData[1]) : null;
        } catch (StringParseException e) {
            throw new InvalidConfigurationException(scalar, e.getMessage());
        }
        final ItemStack foundItem = plugin.getItemStack(itemName);
        if (foundItem == null) {
            try {
                final Material material = SpigotLibs.deserializeMaterial(itemName);
                return new ItemStack(material, amount != null ? amount : 1);
            } catch (StringParseException e) {
                throw new InvalidConfigurationException(scalar, "Could not find any item called: '" + itemName + "'");
            }
        }
        if (amount != null) {
            foundItem.setAmount(amount);
        }
        return foundItem;
    }

}
