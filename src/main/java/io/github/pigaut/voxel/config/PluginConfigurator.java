package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.function.interact.block.*;
import io.github.pigaut.voxel.function.interact.inventory.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.message.config.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.particle.config.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.voxel.sound.config.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.itemstack.*;
import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PluginConfigurator extends SpigotConfigurator {

    private final EnhancedPlugin plugin;
    private final ConditionLoader conditionLoader = new ConditionLoader();
    private final ActionLoader actionLoader = new ActionLoader();

    public PluginConfigurator(@NotNull EnhancedPlugin plugin) {
        this.plugin = plugin;
        addLoader(Placeholder.class, new PlaceholderLoader());
        addLoader(Placeholder[].class, new PlaceholdersLoader());

        addLoader(Amount.class, new AmountLoader());
        addLoader(ItemStack.class, new PluginItemStackLoader());
        addLoader(Flag.class, new FlagLoader());
        addLoader(Message.class, new MessageLoader(plugin));
        addLoader(Hologram.class, new HologramLoader(plugin));
        addLoader(ParticleEffect.class, new ParticleEffectLoader(plugin));
        addLoader(SoundEffect.class, new SoundEffectLoader(plugin));

        addLoader(Condition.class, conditionLoader);
        addLoader(Action.class, actionLoader);
        addLoader(Function.class, new FunctionLoader(plugin));
        addLoader(BlockClickFunction.class, new BlockClickFunctionLoader());
        addLoader(InventoryClickFunction.class, new InventoryClickFunctionLoader());

    }

    public ConditionLoader getConditionLoader() {
        return conditionLoader;
    }

    public ActionLoader getActionLoader() {
        return actionLoader;
    }

    protected class AmountLoader implements ConfigLoader<Amount> {
        @Override
        public @NotNull String getProblemDescription() {
            return "Could not load amount";
        }

        @Override
        public @NotNull Amount loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
            final Double amount = scalar.asDouble().orElse(null);
            if (amount != null) {
                return new Amount.Single(amount);
            }
            final String[] range = scalar.toString().split("-");
            if (range.length != 2) {
                throw new InvalidConfigurationException(scalar, "Expected a number or range");
            }
            try {
                final double min = Deserializers.deserializeDouble(range[0]);
                final double max = Deserializers.deserializeDouble(range[1]);
                if (min >= max) {
                    throw new InvalidConfigurationException(scalar, "Range minimum must be greater than the maximum");
                }
                return new Amount.Range(min, max);
            } catch (DeserializationException e) {
                throw new InvalidConfigurationException(scalar, e.getMessage());
            }
        }
    }

    protected class PluginItemStackLoader extends ItemStackLoader {

        @Override
        public @NotNull ItemStack loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
            final String[] itemData = scalar.toString().split(":");
            final String itemName = itemData[0];
            final Integer amount;
            try {
                amount = itemData.length == 2 ? Deserializers.deserializeInteger(itemData[1]) : null;
            } catch (DeserializationException e) {
                throw new InvalidConfigurationException(scalar, e.getMessage());
            }
            final ItemStack foundItem = plugin.getItemStack(itemName);
            if (foundItem == null) {
                try {
                    final Material material = SpigotLibs.deserializeMaterial(itemName);
                    return new ItemStack(material, amount != null ? amount : 1);
                } catch (DeserializationException e) {
                    throw new InvalidConfigurationException(scalar, "Could not find any item called: '" + itemName + "'");
                }
            }
            if (amount != null) {
                foundItem.setAmount(amount);
            }
            return foundItem;
        }
    }

    protected class FlagLoader implements ScalarLoader<Flag> {
        @Override
        public @NotNull Flag loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
            final String flagName = scalar.toString();
            final Flag foundFlag = plugin.getFlag(flagName);
            if (foundFlag == null) {
                throw new InvalidConfigurationException(scalar, "Could not find any flag called: '" + flagName + "'");
            }
            return foundFlag;
        }
    }


}
