package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.config.function.*;
import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.function.action.block.*;
import io.github.pigaut.voxel.function.action.player.*;
import io.github.pigaut.voxel.function.action.server.*;
import io.github.pigaut.voxel.function.condition.player.*;
import io.github.pigaut.voxel.function.condition.server.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.itemstack.*;
import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PluginConfigurator<P extends EnhancedPlugin> extends SpigotConfigurator {

    protected final P plugin;

    public PluginConfigurator(@NotNull P plugin) {
        this.plugin = plugin;

        addLoader(ItemStack.class, new PluginItemStackLoader());
        addLoader(Flag.class, new FlagLoader());

        addLoader(Message.class, new MessageLoader(plugin));
        addLoader(Function.class, new FunctionLoader(plugin));
        addLoader(BlockClickFunction.class, new BlockClickFunctionLoader());

        //Conditions
        addLoader(OnlinePlayersCondition.class, OnlinePlayersCondition.newConfigLoader());
        addLoader(MinimumOnlinePlayersCondition.class, MinimumOnlinePlayersCondition.newConfigLoader());
        addLoader(MaximumOnlinePlayersCondition.class, MaximumOnlinePlayersCondition.newConfigLoader());
        addLoader(PlayerHasPermission.class, PlayerHasPermission.newConfigLoader());
        addLoader(PlayerHasFlag.class, PlayerHasFlag.newConfigLoader());

        //Server Actions
        addLoader(Broadcast.class, Broadcast.newConfigLoader());
        addLoader(StrikeLightning.class, StrikeLightning.newConfigLoader());
        addLoader(DropItemOnGround.class, DropItemOnGround.newConfigLoader());

        //Player Actions
        addLoader(AddPlayerExp.class, AddPlayerExp.newConfigLoader());
        addLoader(RemovePlayerExp.class, RemovePlayerExp.newConfigLoader());
        addLoader(SetPlayerExp.class, SetPlayerExp.newConfigLoader());
        addLoader(HealPlayer.class, HealPlayer.newConfigLoader());
        addLoader(DamagePlayer.class, DamagePlayer.newConfigLoader());
        addLoader(ExecutePlayerCommand.class, ExecutePlayerCommand.newConfigLoader());
        addLoader(ExecuteOpCommand.class, ExecuteOpCommand.newConfigLoader());
        addLoader(SendChatMessage.class, SendChatMessage.newConfigLoader());
        addLoader(RemovePlayerFlag.class, RemovePlayerFlag.newConfigLoader());
        addLoader(SetFlight.class, SetFlight.newConfigLoader());
        addLoader(StrikePlayerWithLightning.class, StrikePlayerWithLightning.newConfigLoader());
        addLoader(TeleportPlayer.class, TeleportPlayer.newConfigLoader());
        addLoader(GivePlayerItem.class, GivePlayerItem.newConfigLoader());
        addLoader(AddPlayerFlag.class, AddPlayerFlag.newConfigLoader());
        addLoader(SendMessage.class, SendMessage.newConfigLoader());
        addLoader(SetPlayerCursorItem.class, SetPlayerCursorItem.newConfigLoader());
        addLoader(ClearPlayerInventory.class, ClearPlayerInventory.newConfigLoader());
        addLoader(CachePlayerValue.class, CachePlayerValue.newConfigLoader());
        addLoader(OpenEnderChest.class, OpenEnderChest.newConfigLoader());

        //Block Actions
        addLoader(StrikeBlockWithLightning.class, StrikeBlockWithLightning.newConfigLoader());
        addLoader(DropItemOnBlock.class, DropItemOnBlock.newConfigLoader());

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
