package io.github.pigaut.voxel.function.action;

import io.github.pigaut.voxel.function.action.block.*;
import io.github.pigaut.voxel.function.action.player.*;
import io.github.pigaut.voxel.function.action.server.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ActionLoader extends AbstractLoader<Action> {

    public ActionLoader() {
        //server
        addLoader("BROADCAST", (BranchLoader<Action>) branch ->
                new ServerBroadcast(branch.getString("text", 1)));

        addLoader("LIGHTNING", (BranchLoader<Action>) branch ->
                new StrikeLightning(
                        branch.get("location", 1, Location.class),
                        branch.getOptionalBoolean("do-damage", 2).orElse(true)
                ));

        addLoader("CONSOLE_COMMAND", (BranchLoader<Action>) branch ->
                new ExecuteConsoleCommand(branch.getString("command", 1)));

        addLoader("DROP_ITEM", (BranchLoader<Action>) branch ->
                new DropItem(
                        branch.get("item", 1, ItemStack.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("PARTICLE", (BranchLoader<Action>) branch ->
                new SpawnParticle(
                        branch.get("particle", 1, ParticleEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        addLoader("SOUND", (BranchLoader<Action>) branch ->
                new PlaySound(
                        branch.get("sound", 1, SoundEffect.class),
                        branch.get("location", 2, Location.class)
                ));

        //Block Actions
        addLoader("STRIKE_BLOCK", (BranchLoader<Action>) branch ->
                new StrikeBlockWithLightning(branch.getOptionalBoolean("do-damage", 1).orElse(true)));

        addLoader("DROP_AT_BLOCK", (BranchLoader<Action>) branch ->
                new DropItemOnBlock(branch.get("item", 1, ItemStack.class)));

        addLoader("PARTICLE_AT_BLOCK", (BranchLoader<Action>) branch ->
                new SpawnParticleOnBlock(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SOUND_AT_BLOCK", (BranchLoader<Action>) branch ->
                new PlaySoundOnBlock(branch.get("sound", 1, SoundEffect.class)));

        //player

        addLoader("DROP_AT_PLAYER", (BranchLoader<Action>) branch ->
                new DropItemOnPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("PARTICLE_AT_PLAYER", (BranchLoader<Action>) branch ->
                new SpawnParticleOnPlayer(branch.get("particle", 1, ParticleEffect.class)));

        addLoader("SOUND_AT_PLAYER", (BranchLoader<Action>) branch ->
                new PlaySoundOnPlayer(branch.get("sound", 1, SoundEffect.class)));

        addLoader("GIVE_EXP", (BranchLoader<Action>) branch ->
                new GiveExpToPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("GIVE_FLAG", (BranchLoader<Action>) branch ->
                new GiveFlagToPlayer(branch.get("flag", 1, Flag.class)));

        addLoader("GIVE_ITEM", (BranchLoader<Action>) branch ->
                new GiveItemToPlayer(branch.get("item", 1, ItemStack.class)));

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("GIVE_MONEY", (BranchLoader<Action>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("TAKE_EXP", (BranchLoader<Action>) branch ->
                new TakeExpFromPlayer(branch.get("amount", 1, Amount.class)));

        addLoader("TAKE_FLAG", (BranchLoader<Action>) branch ->
                new TakeFlagFromPlayer(branch.getString("flag", 1)));

        addLoader("TAKE_ITEM", (BranchLoader<Action>) branch ->
                new TakeItemFromPlayer(branch.get("item", 1, ItemStack.class)));

        addLoader("TAKE_MONEY", (BranchLoader<Action>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("SET_EXP", (BranchLoader<Action>) branch ->
                new SetPlayerExp(branch.get("amount", 1, Amount.class)));

        addLoader("HEAL", (BranchLoader<Action>) branch ->
                new HealPlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(20))));

        addLoader("DAMAGE", (BranchLoader<Action>) branch ->
                new DamagePlayer(branch.getOptional("amount", 1, Amount.class).orElse(Amount.of(2))));

        addLoader("COMMAND", (BranchLoader<Action>) branch ->
                new ExecutePlayerCommand(branch.getString("command", 1)));

        addLoader("OP_COMMAND", (BranchLoader<Action>) branch ->
                new ExecuteOpCommand(branch.getString("command", 1)));

        addLoader("CHAT_MESSAGE", (BranchLoader<Action>) branch ->
                new SendChatMessage(branch.getString("message", 1)));

        addLoader("MESSAGE", (BranchLoader<Action>) branch ->
                new SendMessage(branch.get("message", 1, Message.class)));

        addLoader("STRIKE_PLAYER", (BranchLoader<Action>) branch ->
                new StrikePlayerWithLightning(branch.getOptionalBoolean("do-damage", 1).orElse(true)));

        addLoader("SET_FLIGHT", (BranchLoader<Action>) branch ->
                new SetPlayerFlight(branch.getOptionalBoolean("enabled", 1).orElse(true)));

        addLoader("TELEPORT", (BranchLoader<Action>) branch ->
                new TeleportPlayer(branch.get("location", 1, Location.class)));

        addLoader("SET_CURSOR_ITEM", (BranchLoader<Action>) branch ->
                new SetPlayerCursorItem(branch.get("item", 1, ItemStack.class)));

        addLoader("CLEAR_INVENTORY", (BranchLoader<Action>) branch ->
                new ClearPlayerInventory(branch.getOptionalBoolean("remove-armor", 1).orElse(true)));

        addLoader("OPEN_ENDER_CHEST", (BranchLoader<Action>) branch ->
                new OpenEnderChest());

        addLoader("PLAYER_CACHE", (BranchLoader<Action>) branch ->
                new CachePlayerValue(
                        branch.getString("id", 1),
                        branch.getString("value", 2)
                ));
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid action";
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

    public ConfigLoader<? extends Action> getLoader(ConfigField field, String id) throws InvalidConfigurationException {
        final ConfigLoader<? extends Action> loader = getLoader(id);
        if (loader == null) {
            throw new InvalidConfigurationException(field,
                    "Could not find '" + StringFormatter.toConstantCase(id) + "' action");
        }
        return loader;
    }

}
