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
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ActionLoader extends AbstractLoader<SystemAction> {

    private final EnhancedPlugin plugin;

    public ActionLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;

        //server
        addLoader("BROADCAST", (Line<SystemAction>) line ->
                new ServerBroadcast(line.getRequiredString(1)));

        addLoader("LIGHTNING", (Line<SystemAction>) line ->
                new StrikeLightning(
                        line.get("world", World.class).throwOrElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z"),
                        line.getBoolean("doDamage|damage").throwOrElse(true)
                ));

        addLoader("CONSOLE_COMMAND", (Line<SystemAction>) line ->
                new ExecuteConsoleCommand(line.getRequiredString(1)));

        addLoader("DROP_ITEM", (Line<SystemAction>) line ->
                new DropItem(
                        line.getRequired(1, ItemStack.class),
                        line.get("world", World.class).throwOrElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("DROP_EXP", (Line<SystemAction>) line ->
                new DropExp(
                        line.getRequired(1, Amount.class),
                        line.get("world", World.class).throwOrElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z"),
                        line.get("orbs|orbCount", Amount.class).throwOrElse(Amount.ONE)
                ));

        addLoader("SPAWN_PARTICLE", (Line<SystemAction>) line ->
                new SpawnParticle(
                        line.getRequired(1, ParticleEffect.class),
                        line.get("world", World.class).throwOrElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("PARTICLE", (Line<SystemAction>) line ->
                new SpawnParticle(
                        line.getRequired(1, ParticleEffect.class),
                        line.get("world", World.class).throwOrElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("PLAY_SOUND", (Line<SystemAction>) line ->
                new PlaySound(
                        line.getRequired(1, SoundEffect.class),
                        line.get("world", World.class).orElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("SOUND", (Line<SystemAction>) line ->
                new PlaySound(
                        line.getRequired(1, SoundEffect.class),
                        line.get("world", World.class).orElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        //Function Actions

        addLoader("RETURN", (Line<SystemAction>) line ->
                new ReturnAction());

        addLoader("STOP", (Line<SystemAction>) line ->
                new StopAction());

        addLoader("GOTO", (Line<SystemAction>) line ->
                new GotoAction(line.getRequiredInteger(1)));

        //Event Actions

        addLoader("CANCEL_EVENT", (Line<SystemAction>) line ->
                new CancelEventAction(true));

        addLoader("CANCEL", (Line<SystemAction>) line ->
                new CancelEventAction(true));

        addLoader("SET_CANCELLED", (Line<SystemAction>) line ->
                new CancelEventAction(line.getRequiredBoolean(1)));

        //Block Actions
        addLoader("STRIKE_BLOCK", (Line<SystemAction>) line ->
                new StrikeBlockWithLightning(line.getBoolean("doDamage|damage").orElse(true)));

        addLoader("DROP_AT_BLOCK", (Line<SystemAction>) line ->
                new DropItemAtBlock(line.getRequired(1, ItemStack.class)));

        addLoader("DROP_ITEM_AT_BLOCK", (Line<SystemAction>) line ->
                new DropItemAtBlock(line.getRequired(1, ItemStack.class)));

        addLoader("DROP_EXP_AT_BLOCK", (Line<SystemAction>) line ->
                new DropExpAtBlock(
                        line.getRequired(1, Amount.class),
                        line.get("orbs|orbCount", Amount.class).orElse(Amount.ONE)
                ));

        addLoader("PARTICLE_AT_BLOCK", (Line<SystemAction>) line ->
                new SpawnParticleOnBlock(line.getRequired(1, ParticleEffect.class)));

        addLoader("SPAWN_PARTICLE_AT_BLOCK", (Line<SystemAction>) line ->
                new SpawnParticleOnBlock(line.getRequired(1, ParticleEffect.class)));

        addLoader("SOUND_AT_BLOCK", (Line<SystemAction>) line ->
                new PlaySoundOnBlock(line.getRequired(1, SoundEffect.class)));

        addLoader("PLAY_SOUND_AT_BLOCK", (Line<SystemAction>) line ->
                new PlaySoundOnBlock(line.getRequired(1, SoundEffect.class)));

        //player actions

        addLoader("DROP_AT_PLAYER", (Line<SystemAction>) line ->
                new DropItemAtPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("DROP_ITEM_AT_PLAYER", (Line<SystemAction>) line ->
                new DropItemAtPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("DROP_EXP_AT_PLAYER", (Line<SystemAction>) line ->
                new DropExpAtPlayer(
                        line.getRequired(1, Amount.class),
                        line.get("orbs|orbCount", Amount.class).orElse(Amount.ONE)
                ));

        addLoader("PARTICLE_AT_PLAYER", (Line<SystemAction>) line ->
                new SpawnParticleOnPlayer(line.getRequired(1, ParticleEffect.class)));

        addLoader("SPAWN_PARTICLE_AT_PLAYER", (Line<SystemAction>) line ->
                new SpawnParticleOnPlayer(line.getRequired(1, ParticleEffect.class)));

        addLoader("SOUND_AT_PLAYER", (Line<SystemAction>) line ->
                new PlaySoundOnPlayer(line.getRequired(1, SoundEffect.class)));

        addLoader("PLAY_SOUND_AT_PLAYER", (Line<SystemAction>) line ->
                new PlaySoundOnPlayer(line.getRequired(1, SoundEffect.class)));

        addLoader("GIVE_EXP", (Line<SystemAction>) line ->
                new GiveExpToPlayer(line.getRequired(1, Amount.class)));

        addLoader("GIVE_EXP_TO_PLAYER", (Line<SystemAction>) line ->
                new GiveExpToPlayer(line.getRequired(1, Amount.class)));

        addLoader("GIVE_FLAG", (Line<SystemAction>) line ->
                new GiveFlagToPlayer(line.getRequiredString(1)));

        addLoader("GIVE_FLAG_TO_PLAYER", (Line<SystemAction>) line ->
                new GiveFlagToPlayer(line.getRequiredString(1)));

        addLoader("GIVE_ITEM", (Line<SystemAction>) line ->
                new GiveItemToPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("GIVE_ITEM_TO_PLAYER", (Line<SystemAction>) line ->
                new GiveItemToPlayer(line.getRequired(1, ItemStack.class)));

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("GIVE_MONEY", (Line<SystemAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("GIVE_MONEY_TO_PLAYER", (Line<SystemAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("TAKE_EXP", (Line<SystemAction>) line ->
                new TakeExpFromPlayer(line.getRequired(1, Amount.class)));

        addLoader("TAKE_EXP_FROM_PLAYER", (Line<SystemAction>) line ->
                new TakeExpFromPlayer(line.getRequired(1, Amount.class)));

        addLoader("TAKE_FLAG", (Line<SystemAction>) line ->
                new TakeFlagFromPlayer(line.getRequiredString(1)));

        addLoader("TAKE_FLAG_FROM_PLAYER", (Line<SystemAction>) line ->
                new TakeFlagFromPlayer(line.getRequiredString(1)));

        addLoader("TAKE_ITEM", (Line<SystemAction>) line ->
                new TakeItemFromPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("TAKE_ITEM_FROM_PLAYER", (Line<SystemAction>) line ->
                new TakeItemFromPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("TAKE_MONEY", (Line<SystemAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("TAKE_MONEY_FROM_PLAYER", (Line<SystemAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("SET_EXP", (Line<SystemAction>) line ->
                new SetPlayerExp(line.getRequired(1, Amount.class)));

        addLoader("SET_PLAYER_EXP", (Line<SystemAction>) line ->
                new SetPlayerExp(line.getRequired(1, Amount.class)));

        addLoader("HEAL", (Line<SystemAction>) line ->
                new HealPlayer(line.get(1, Amount.class).orElse(Amount.fixed(20))));

        addLoader("HEAL_PLAYER", (Line<SystemAction>) line ->
                new HealPlayer(line.get(1, Amount.class).orElse(Amount.fixed(20))));

        addLoader("DAMAGE", (Line<SystemAction>) line ->
                new DamagePlayer(line.get(1, Amount.class).orElse(Amount.fixed(2))));

        addLoader("DAMAGE_PLAYER", (Line<SystemAction>) line ->
                new DamagePlayer(line.get(1, Amount.class).orElse(Amount.fixed(2))));

        addLoader("COMMAND", (Line<SystemAction>) line ->
                new ExecutePlayerCommand(line.getRequiredString(1)));

        addLoader("CHAT_MESSAGE", (Line<SystemAction>) line ->
                new SendChatToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_CHAT", (Line<SystemAction>) line ->
                new SendChatToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_ACTIONBAR", (Line<SystemAction>) line ->
                new SendActionbarToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_TITLE", (Line<SystemAction>) line ->
                new SendTitleToPlayer(
                        line.getRequiredString(1, StringColor.FORMATTER),
                        line.getString("subtitle", StringColor.FORMATTER).throwOrElse(""),
                        line.getInteger("fadeIn|fade-in").throwOrElse(10),
                        line.getInteger("stay").throwOrElse(70),
                        line.getInteger("fadeOut|fade-out").throwOrElse(20)
                ));

        addLoader("SEND_HOLOGRAM", (Line<SystemAction>) line ->
                new SendHologramToPlayer(plugin,
                        line.getRequiredString(1, StringColor.FORMATTER),
                        line.getInteger("duration").throwOrElse(60),
                        line.getDouble("radiusX|radius-x|xRadius").throwOrElse(null),
                        line.getDouble("radiusY|radius-y|yRadius").throwOrElse(null),
                        line.getDouble("radiusZ|radius-z|zRadius").throwOrElse(null)
                ));

        addLoader("MESSAGE", (Line<SystemAction>) line ->
                new SendMessage(line.getRequired(1, Message.class)));

        addLoader("SEND_MESSAGE", (Line<SystemAction>) line ->
                new SendMessage(line.getRequired(1, Message.class)));

        addLoader("LIGHTNING_AT_PLAYER", (Line<SystemAction>) line ->
                new StrikePlayerWithLightning(line.getBoolean("doDamage|damage").orElse(true)));

        addLoader("SET_FLIGHT", (Line<SystemAction>) line ->
                new SetPlayerFlight(line.getBoolean(1).orElse(true)));

        addLoader("TELEPORT", (Line<SystemAction>) line ->
                new TeleportPlayer(line.getRequired(1, Location.class)));

        addLoader("TELEPORT_PLAYER", (Line<SystemAction>) line ->
                new TeleportPlayer(line.getRequired(1, Location.class)));

        addLoader("SET_CURSOR_ITEM", (Line<SystemAction>) line ->
                new SetPlayerCursorItem(line.getRequired(1, ItemStack.class)));

        addLoader("OPEN_ENDER_CHEST", (Line<SystemAction>) line ->
                new OpenEnderChest());

        addLoader("CLOSE_INVENTORY", (Line<SystemAction>) line ->
                new CloseInventory());

        addLoader("PLAYER_CACHE", (Line<SystemAction>) line ->
                new CachePlayerValue(
                        line.getRequiredString(1),
                        line.getRequiredString(2)
                ));
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid action";
    }

    @Override
    public @NotNull SystemAction loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final ConfigLine line = scalar.toLine();
        final String actionId = line.getRequiredString(0);

        final ConfigLoader<? extends SystemAction> loader = getLoader(actionId);
        if (loader == null) {
            throw new InvalidConfigurationException(line,
                    "Could not find action with name: " + CaseFormatter.toCamelCase(actionId));
        }

        SystemAction action = loader.loadFromScalar(scalar);

        final Integer repetitions = line.getInteger("repetitions|loops")
                .filter(Predicates.isPositive(), "Repetitions must be greater than 0")
                .throwOrElse(null);

        final Integer interval = line.getInteger("interval|period")
                .filter(Predicates.notNull(repetitions), "Repetitions must be set to use interval delay")
                .filter(Predicates.isPositive(), "Interval must be greater than 0")
                .throwOrElse(null);

        if (interval != null) {
            action = new PeriodicAction(plugin, action, interval, repetitions);
        }
        else if (repetitions != null) {
            action = new RepeatedAction(action, repetitions);
        }

        final Integer delay = line.getInteger("delay")
                .filter(Predicates.isPositive(), "Delay must be greater than 0")
                .throwOrElse(null);

        if (delay != null) {
            action = new DelayedAction(plugin, action, delay);
        }

        final Double chance = line.getDouble("chance")
                .filter(Predicates.range(0, 1), "Chance must be a value between 0.0 to 1.0")
                .throwOrElse(null);

        if (chance != null) {
            action = new ChanceAction(action, chance);
        }

        return action;
    }

    @Override
    public @NotNull SystemAction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiAction(sequence.getAll(SystemAction.class));
    }

}
