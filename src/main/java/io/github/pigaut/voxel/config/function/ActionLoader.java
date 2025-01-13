package io.github.pigaut.voxel.config.function;

import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.action.block.*;
import io.github.pigaut.voxel.function.action.player.*;
import io.github.pigaut.voxel.function.action.server.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ActionLoader extends AbstractLoader<Action> {

    public ActionLoader() {
        //server
        addLoader("LIGHTNING", ConstructorLoader.from(Location.class, StrikeLightning::new));
        addLoader("DROP_ITEM", DropItemOnGround.newConfigLoader());
        addLoader("BROADCAST", ConstructorLoader.fromString(ServerBroadcast::new));
        addLoader("CONSOLE_COMMAND", ConstructorLoader.fromString(ExecuteConsoleCommand::new));

        //Block Actions
        addLoader("STRIKE_BLOCK", ConstructorLoader.fromBoolean(StrikeBlockWithLightning::new));
        addLoader("DROP_ON_BLOCK", ConstructorLoader.from(ItemStack.class, DropItemOnBlock::new));

        //player
        addLoader("ADD_EXP", ConstructorLoader.fromInteger(AddPlayerExp::new));
        addLoader("REMOVE_EXP", ConstructorLoader.fromInteger(RemovePlayerExp::new));
        addLoader("SET_EXP", ConstructorLoader.fromInteger(SetPlayerExp::new));

        addLoader("HEAL", ConstructorLoader.fromInteger(HealPlayer::new));
        addLoader("DAMAGE", ConstructorLoader.fromInteger(DamagePlayer::new));

        addLoader("COMMAND", ConstructorLoader.fromString(ExecutePlayerCommand::new));
        addLoader("OP_COMMAND", ConstructorLoader.fromString(ExecuteOpCommand::new));

        addLoader("CHAT_MESSAGE", ConstructorLoader.fromString(SendChatMessage::new));
        addLoader("MESSAGE", ConstructorLoader.from(Message.class, SendMessage::new));

        addLoader("ADD_FLAG", ConstructorLoader.from(Flag.class, AddPlayerFlag::new));
        addLoader("REMOVE_FLAG", ConstructorLoader.fromString(RemovePlayerFlag::new));

        addLoader("SET_FLIGHT", ConstructorLoader.fromBoolean(SetPlayerFlight::new));
        addLoader("STRIKE_PLAYER", ConstructorLoader.fromBoolean(StrikePlayerWithLightning::new));
        addLoader("TELEPORT", ConstructorLoader.from(Location.class, TeleportPlayer::new));
        addLoader("GIVE_ITEM", ConstructorLoader.from(ItemStack.class, GiveItemToPlayer::new));
        addLoader("DROP_ON_PLAYER", ConstructorLoader.from(ItemStack.class, DropItemOnPlayer::new));
        addLoader("SET_CURSOR_ITEM", ConstructorLoader.from(ItemStack.class, SetPlayerCursorItem::new));
        addLoader("CLEAR_INVENTORY", ConstructorLoader.fromBoolean(ClearPlayerInventory::new));
        addLoader("OPEN_ENDER_CHEST", ConstructorLoader.from(OpenEnderChest::new));

        addLoader("PLAYER_CACHE", ConstructorLoader.fromSection(section -> {
            final String id = section.getString("id");
            final Object value = section.getScalar("value").getValue();
            return new CachePlayerValue(id, value);
        }));
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "Could not load action";
    }

    public ConfigLoader<? extends Action> getLoader(ConfigField field, String id) {
        final ConfigLoader<? extends Action> loader = getLoader(id);
        if (loader == null) {
            throw new InvalidConfigurationException(field,
                    "Could not find '" + StringFormatter.toConstantCase(id) + "' action");
        }
        return loader;
    }

    @Override
    public @NotNull Action loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final ConfigLoader<? extends Action> loader = getLoader(section, section.getString("action"));
        return loader.loadFromSection(section);
    }

    @Override
    public @NotNull Action loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final ConfigLoader<? extends Action> loader = getLoader(sequence, sequence.getString(0));
        return loader.loadFromSequence(sequence);
    }

}
