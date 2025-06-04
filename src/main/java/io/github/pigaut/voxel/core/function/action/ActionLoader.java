package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.action.block.*;
import io.github.pigaut.voxel.core.function.action.event.*;
import io.github.pigaut.voxel.core.function.action.player.*;
import io.github.pigaut.voxel.core.function.action.server.*;
import io.github.pigaut.voxel.core.function.action.system.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public class ActionLoader extends AbstractLoader<SystemAction> {

    public ActionLoader() {
        //server

        addLoader("BROADCAST", (BranchLoader<SystemAction>) branch ->
                new ServerBroadcast(branch.getString("text", 1)));

        addLoader("LIGHTNING", (BranchLoader<SystemAction>) branch ->
                new StrikeLightning(
                        branch.get("location", 1, Location.class),
                        branch.getOptionalBoolean("do-damage", 2).orElse(true)
                ));

        addLoader("CONSOLE_COMMAND", (BranchLoader<SystemAction>) branch ->
                new ExecuteConsoleCommand(branch.getString("command", 1)));

        addLoader("DROP_ITEM", (BranchLoader<SystemAction>) branch ->
                new DropItem(
                        branch.get("item", 1, ItemStack.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("DROP_EXP", (BranchLoader<SystemAction>) branch ->
                new DropExp(
                        branch.get("exp", 1, Amount.class),
                        branch.get("orbs", 2, Amount.class),
                        branch.get("location", 3, Location.class)
                ));

        addLoader("SPAWN_PARTICLE", (BranchLoader<SystemAction>) branch ->
                new SpawnParticle(
                        branch.get("particle", 1, ParticleEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("PARTICLE", (BranchLoader<SystemAction>) branch ->
                new SpawnParticle(
                        branch.get("particle", 1, ParticleEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("PLAY_SOUND", (BranchLoader<SystemAction>) branch ->
                new PlaySound(
                        branch.get("sound", 1, SoundEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("SOUND", (BranchLoader<SystemAction>) branch ->
                new PlaySound(
                        branch.get("sound", 1, SoundEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        //Function Actions

        addLoader("RETURN", (BranchLoader<SystemAction>) branch ->
                new ReturnAction());

        addLoader("STOP", (BranchLoader<SystemAction>) branch ->
                new StopAction());

        addLoader("GOTO", (BranchLoader<SystemAction>) branch ->
                new GotoAction(branch.getInteger("line", 1)));

        //Event Actions

        addLoader("CANCEL_EVENT", (BranchLoader<SystemAction>) branch ->
                new CancelEventAction(true));

        addLoader("CANCEL", (BranchLoader<SystemAction>) branch ->
                new CancelEventAction(true));

        addLoader("SET_CANCELLED", (BranchLoader<SystemAction>) branch ->
                new CancelEventAction(branch.getBoolean("cancelled", 1)));

        //Block Actions
        addLoader("STRIKE_BLOCK", (BranchLoader<SystemAction>) branch ->
                new StrikeBlockWithLightning(branch.getOptionalBoolean("do-damage", 1).orElse(true)));

        addLoader("DROP_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new DropItemAtBlock(branch.get("item", 1, ItemStack.class)));

        addLoader("DROP_ITEM_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new DropItemAtBlock(branch.get("item", 1, ItemStack.class)));

        addLoader("DROP_EXP_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new DropExpAtBlock(
                        branch.get("exp", 1, Amount.class),
                        branch.get("orbs", 2, Amount.class)
                ));

        addLoader("PARTICLE_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new SpawnParticleOnBlock(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SPAWN_PARTICLE_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new SpawnParticleOnBlock(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SOUND_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new PlaySoundOnBlock(branch.get("sound", 1, SoundEffect.class)));

        addLoader("PLAY_SOUND_AT_BLOCK", (BranchLoader<SystemAction>) branch ->
                new PlaySoundOnBlock(branch.get("sound", 1, SoundEffect.class)));

        //player actions

        addLoader("DROP_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new DropItemAtPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("DROP_ITEM_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new DropItemAtPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("DROP_EXP_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new DropExpAtPlayer(
                        branch.get("exp", 1, Amount.class),
                        branch.get("orbs", 2, Amount.class)
                ));

        addLoader("PARTICLE_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new SpawnParticleOnPlayer(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SPAWN_PARTICLE_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new SpawnParticleOnPlayer(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SOUND_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new PlaySoundOnPlayer(branch.get("sound", 1, SoundEffect.class)));

        addLoader("PLAY_SOUND_AT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new PlaySoundOnPlayer(branch.get("sound", 1, SoundEffect.class)));

        addLoader("GIVE_EXP", (BranchLoader<SystemAction>) branch ->
                new GiveExpToPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("GIVE_EXP_TO_PLAYER", (BranchLoader<SystemAction>) branch ->
                new GiveExpToPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("GIVE_FLAG", (BranchLoader<SystemAction>) branch ->
                new GiveFlagToPlayer(branch.getString("flag", 1)));

        addLoader("GIVE_FLAG_TO_PLAYER", (BranchLoader<SystemAction>) branch ->
                new GiveFlagToPlayer(branch.getString("flag", 1)));

        addLoader("GIVE_ITEM", (BranchLoader<SystemAction>) branch ->
                new GiveItemToPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("GIVE_ITEM_TO_PLAYER", (BranchLoader<SystemAction>) branch ->
                new GiveItemToPlayer(branch.get("item", 1, ItemStack.class)));

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("GIVE_MONEY", (BranchLoader<SystemAction>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("GIVE_MONEY_TO_PLAYER", (BranchLoader<SystemAction>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("TAKE_EXP", (BranchLoader<SystemAction>) branch ->
                new TakeExpFromPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("TAKE_EXP_FROM_PLAYER", (BranchLoader<SystemAction>) branch ->
                new TakeExpFromPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("TAKE_FLAG", (BranchLoader<SystemAction>) branch ->
                new TakeFlagFromPlayer(branch.getString("flag", 1)));

        addLoader("TAKE_FLAG_FROM_PLAYER", (BranchLoader<SystemAction>) branch ->
                new TakeFlagFromPlayer(branch.getString("flag", 1)));

        addLoader("TAKE_ITEM", (BranchLoader<SystemAction>) branch ->
                new TakeItemFromPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("TAKE_ITEM_FROM_PLAYER", (BranchLoader<SystemAction>) branch ->
                new TakeItemFromPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("TAKE_MONEY", (BranchLoader<SystemAction>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("TAKE_MONEY_FROM_PLAYER", (BranchLoader<SystemAction>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("SET_EXP", (BranchLoader<SystemAction>) branch ->
                new SetPlayerExp(branch.get("amount", 1, Amount.class)));

        addLoader("SET_PLAYER_EXP", (BranchLoader<SystemAction>) branch ->
                new SetPlayerExp(branch.get("amount", 1, Amount.class)));

        addLoader("HEAL", (BranchLoader<SystemAction>) branch ->
                new HealPlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(20))));

        addLoader("HEAL_PLAYER", (BranchLoader<SystemAction>) branch ->
                new HealPlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(20))));

        addLoader("DAMAGE", (BranchLoader<SystemAction>) branch ->
                new DamagePlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(2))));

        addLoader("DAMAGE_PLAYER", (BranchLoader<SystemAction>) branch ->
                new DamagePlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(2))));

        addLoader("COMMAND", (BranchLoader<SystemAction>) branch ->
                new ExecutePlayerCommand(branch.getString("command", 1)));

        addLoader("CHAT_MESSAGE", (BranchLoader<SystemAction>) branch ->
                new SendChatMessage(branch.getString("message", 1)));

        addLoader("SEND_CHAT_TO_PLAYER", (BranchLoader<SystemAction>) branch ->
                new SendChatMessage(branch.getString("message", 1)));

        addLoader("MESSAGE", (BranchLoader<SystemAction>) branch ->
                new SendMessage(branch.get("message", 1, Message.class)));

        addLoader("SEND_MESSAGE_TO_PLAYER", (BranchLoader<SystemAction>) branch ->
                new SendMessage(branch.get("message", 1, Message.class)));

        addLoader("CUSTOM_MESSAGE", (BranchLoader<SystemAction>) branch ->
                new SendMessage(branch.get("message", 1, Message.class)));

        addLoader("STRIKE_PLAYER", (BranchLoader<SystemAction>) branch ->
                new StrikePlayerWithLightning(branch.getOptionalBoolean("do-damage", 1).orElse(true)));

        addLoader("SET_FLIGHT", (BranchLoader<SystemAction>) branch ->
                new SetPlayerFlight(branch.getOptionalBoolean("enabled", 1).orElse(true)));

        addLoader("TELEPORT", (BranchLoader<SystemAction>) branch ->
                new TeleportPlayer(branch.get("location", 1, Location.class)));

        addLoader("TELEPORT_PLAYER", (BranchLoader<SystemAction>) branch ->
                new TeleportPlayer(branch.get("location", 1, Location.class)));

        addLoader("SET_CURSOR_ITEM", (BranchLoader<SystemAction>) branch ->
                new SetPlayerCursorItem(branch.get("item", 1, ItemStack.class)));

        addLoader("OPEN_ENDER_CHEST", (BranchLoader<SystemAction>) branch ->
                new OpenEnderChest());

        addLoader("CLOSE_INVENTORY", (BranchLoader<SystemAction>) branch ->
                new CloseInventory());

        addLoader("PLAYER_CACHE", (BranchLoader<SystemAction>) branch ->
                new CachePlayerValue(
                        branch.getString("id", 1),
                        branch.getString("value", 2)
                ));
    }

    public ConfigLoader<? extends SystemAction> getLoader(ConfigField field, String id) throws InvalidConfigurationException {
        final ConfigLoader<? extends SystemAction> loader = getLoader(id);
        if (loader == null) {
            throw new InvalidConfigurationException(field,
                    "Could not find action with name: " + StringFormatter.toCamelCase(id));
        }
        return loader;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid action";
    }

    private static final Pattern INLINE_ACTION_PATTERN = Pattern.compile("<([^>]*)>|(\\S+)");

    @Override
    public @NotNull SystemAction loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final ConfigSequence splitString = scalar.split(INLINE_ACTION_PATTERN);
        final ConfigLoader<? extends SystemAction> loader = getLoader(splitString, splitString.getString(0));
        return loader.loadFromSequence(splitString);
    }

    @Override
    public @NotNull SystemAction loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final ConfigLoader<? extends SystemAction> loader = getLoader(section, section.getString("action"));
        return loader.loadFromSection(section);
    }

    @Override
    public @NotNull SystemAction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiAction(sequence.getAll(SystemAction.class));
    }

}
