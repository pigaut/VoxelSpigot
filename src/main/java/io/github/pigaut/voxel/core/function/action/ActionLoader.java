package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.bukkit.*;
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
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ActionLoader extends AbstractLoader<FunctionAction> {

    private final EnhancedPlugin plugin;

    public ActionLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;

        //server
        addLoader("BROADCAST", (Line<FunctionAction>) line ->
                new ServerBroadcast(line.getRequiredString(1)));

        addLoader("LIGHTNING", (Line<FunctionAction>) line ->
                new StrikeLightning(
                        line.get("world", World.class).withDefault(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z"),
                        line.getBoolean("doDamage|damage").withDefault(true)
                ));

        addLoader("CONSOLE_COMMAND", (Line<FunctionAction>) line ->
                new ExecuteConsoleCommand(line.getRequiredString(1)));

        addLoader("DROP_ITEM", (Line<FunctionAction>) line -> {
            ItemStack item = line.getRequired(1, ItemStack.class);
            Amount amount = line.get("amount|dropAmount", Amount.class)
                    .withDefault(Amount.ONE);
            boolean doFortune = line.getBoolean("doFortune|fortune")
                    .withDefault(plugin.getOptions().shouldDoFortune(item.getType()));

            return new DropItem(item, amount, doFortune,
                    line.get("world", World.class).withDefault(SpigotServer.getDefaultWorld()),
                    line.getRequiredDouble("x"),
                    line.getRequiredDouble("y"),
                    line.getRequiredDouble("z"));
        });

        addLoader("DROP_EXP", (Line<FunctionAction>) line ->
                new DropExp(
                        line.getRequired(1, Amount.class),
                        line.get("orbs|orbCount", Amount.class).withDefault(null),
                        line.get("world", World.class).withDefault(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("SPAWN_PARTICLE", (Line<FunctionAction>) line ->
                new SpawnParticle(
                        line.getRequired(1, ParticleEffect.class),
                        line.get("world", World.class).withDefault(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("PARTICLE", (Line<FunctionAction>) line ->
                new SpawnParticle(
                        line.getRequired(1, ParticleEffect.class),
                        line.get("world", World.class).withDefault(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("PLAY_SOUND", (Line<FunctionAction>) line ->
                new PlaySound(
                        line.getRequired(1, SoundEffect.class),
                        line.get("world", World.class).orElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        addLoader("SOUND", (Line<FunctionAction>) line ->
                new PlaySound(
                        line.getRequired(1, SoundEffect.class),
                        line.get("world", World.class).orElse(SpigotServer.getDefaultWorld()),
                        line.getRequiredDouble("x"),
                        line.getRequiredDouble("y"),
                        line.getRequiredDouble("z")
                ));

        //Function Actions

        addLoader("RETURN", (Line<FunctionAction>) line ->
                new ReturnAction());

        addLoader("STOP", (Line<FunctionAction>) line ->
                new StopAction());

        addLoader("GOTO", (Line<FunctionAction>) line ->
                new GotoAction(line.getInteger(1)
                        .filter(Predicates.isPositive(), "Value must be greater than or equal to 1")
                        .orThrow() - 1
                ));

        //Event Actions

        addLoader("CANCEL_EVENT", (Line<FunctionAction>) line ->
                new CancelEventAction(true));

        addLoader("CANCEL", (Line<FunctionAction>) line ->
                new CancelEventAction(true));

        addLoader("SET_CANCELLED", (Line<FunctionAction>) line ->
                new CancelEventAction(line.getRequiredBoolean(1)));

        //Block Actions
        addLoader("STRIKE_BLOCK", (Line<FunctionAction>) line ->
                new StrikeBlockWithLightning(line.getBoolean("doDamage|damage").withDefault(true)));

        addLoader("DROP_ITEM_AT_BLOCK", (Line<FunctionAction>) line -> {
            ItemStack item = line.getRequired(1, ItemStack.class);
            Amount amount = line.get("amount|dropAmount", Amount.class)
                    .withDefault(Amount.ONE);
            boolean doFortune = line.getBoolean("doFortune|fortune")
                    .withDefault(plugin.getOptions().shouldDoFortune(item.getType()));
            return new DropItemAtBlock(item, amount, doFortune);
        });

        addLoader("DROP_EXP_AT_BLOCK", (Line<FunctionAction>) line ->
                new DropExpAtBlock(
                        line.getRequired(1, Amount.class),
                        line.get("orbs|orbCount", Amount.class).withDefault(null)
                ));

        addLoader("PARTICLE_AT_BLOCK", (Line<FunctionAction>) line ->
                new SpawnParticleOnBlock(line.getRequired(1, ParticleEffect.class)));

        addLoader("SPAWN_PARTICLE_AT_BLOCK", (Line<FunctionAction>) line ->
                new SpawnParticleOnBlock(line.getRequired(1, ParticleEffect.class)));

        addLoader("SOUND_AT_BLOCK", (Line<FunctionAction>) line ->
                new PlaySoundOnBlock(line.getRequired(1, SoundEffect.class)));

        addLoader("PLAY_SOUND_AT_BLOCK", (Line<FunctionAction>) line ->
                new PlaySoundOnBlock(line.getRequired(1, SoundEffect.class)));

        //player actions

        addLoader("DROP_ITEM_AT_PLAYER", (Line<FunctionAction>) line -> {
            ItemStack item = line.getRequired(1, ItemStack.class);
            Amount amount = line.get("amount|dropAmount", Amount.class)
                    .withDefault(Amount.ONE);
            boolean doFortune = line.getBoolean("doFortune|fortune")
                    .withDefault(plugin.getOptions().shouldDoFortune(item.getType()));
            return new DropItemAtPlayer(item, amount, doFortune);
        });

        addLoader("DROP_EXP_AT_PLAYER", (Line<FunctionAction>) line ->
                new DropExpAtPlayer(
                        line.getRequired(1, Amount.class),
                        line.get("orbs|orbCount", Amount.class).withDefault(null)
                ));

        addLoader("SPAWN_PARTICLE_AT_PLAYER", (Line<FunctionAction>) line ->
                new SpawnParticleOnPlayer(line.getRequired(1, ParticleEffect.class)));

        addLoader("PLAY_SOUND_AT_PLAYER", (Line<FunctionAction>) line ->
                new PlaySoundOnPlayer(line.getRequired(1, SoundEffect.class)));

        addLoader("GIVE_EXP", (Line<FunctionAction>) line ->
                new GiveExpToPlayer(line.getRequired(1, Amount.class)));

        addLoader("GIVE_FLAG", (Line<FunctionAction>) line ->
                new GiveFlagToPlayer(line.getRequiredString(1)));

        addLoader("GIVE_ITEM", (Line<FunctionAction>) line -> {
            ItemStack item = line.getRequired(1, ItemStack.class);
            Amount amount = line.get("amount|dropAmount", Amount.class)
                    .withDefault(Amount.ONE);
            boolean doFortune = line.getBoolean("doFortune|fortune")
                    .withDefault(plugin.getOptions().shouldDoFortune(item.getType()));
            return new GiveItemToPlayer(item, amount, doFortune);
        });

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("GIVE_MONEY", (Line<FunctionAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new GiveMoneyToPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("TAKE_EXP", (Line<FunctionAction>) line ->
                new TakeExpFromPlayer(line.getRequired(1, Amount.class)));

        addLoader("TAKE_FLAG", (Line<FunctionAction>) line ->
                new TakeFlagFromPlayer(line.getRequiredString(1)));

        addLoader("TAKE_ITEM", (Line<FunctionAction>) line ->
                new TakeItemFromPlayer(line.getRequired(1, ItemStack.class)));

        addLoader("TAKE_MONEY", (Line<FunctionAction>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing Vault or economy plugin");
            }
            return new TakeMoneyFromPlayer(economy, line.getRequired(1, Amount.class));
        });

        addLoader("SET_EXP", (Line<FunctionAction>) line ->
                new SetPlayerExp(line.getRequired(1, Amount.class)));

        addLoader("HEAL", (Line<FunctionAction>) line ->
                new HealPlayer(line.get(1, Amount.class).orElse(Amount.fixed(20))));

        addLoader("DAMAGE", (Line<FunctionAction>) line ->
                new DamagePlayer(line.get(1, Amount.class).orElse(Amount.fixed(2))));

        addLoader("COMMAND", (Line<FunctionAction>) line ->
                new ExecutePlayerCommand(line.getRequiredString(1)));

        addLoader("CHAT_MESSAGE", (Line<FunctionAction>) line ->
                new SendChatToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_CHAT", (Line<FunctionAction>) line ->
                new SendChatToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_ACTIONBAR", (Line<FunctionAction>) line ->
                new SendActionbarToPlayer(line.getRequiredString(1, StringColor.FORMATTER)));

        addLoader("SEND_TITLE", (Line<FunctionAction>) line ->
                new SendTitleToPlayer(
                        line.getRequiredString(1, StringColor.FORMATTER),
                        line.getString("subtitle", StringColor.FORMATTER).withDefault(""),
                        line.getInteger("fadeIn|fade-in").withDefault(10),
                        line.getInteger("stay").withDefault(70),
                        line.getInteger("fadeOut|fade-out").withDefault(20)
                ));

        addLoader("SEND_HOLOGRAM", (Line<FunctionAction>) line ->
                new SendHologramToPlayer(plugin,
                        line.getRequiredString(1, StringColor.FORMATTER),
                        line.getInteger("duration").withDefault(60),
                        line.getDouble("radiusX|radius-x|xRadius").withDefault(null),
                        line.getDouble("radiusY|radius-y|yRadius").withDefault(null),
                        line.getDouble("radiusZ|radius-z|zRadius").withDefault(null)
                ));

        addLoader("MESSAGE", (Line<FunctionAction>) line ->
                new SendMessage(line.getRequired(1, Message.class)));

        addLoader("SEND_MESSAGE", (Line<FunctionAction>) line ->
                new SendMessage(line.getRequired(1, Message.class)));

        addLoader("LIGHTNING_AT_PLAYER", (Line<FunctionAction>) line ->
                new StrikePlayerWithLightning(line.getBoolean("doDamage|damage").orElse(true)));

        addLoader("SET_FLIGHT", (Line<FunctionAction>) line ->
                new SetPlayerFlight(line.getBoolean(1).orElse(true)));

        addLoader("TELEPORT", (Line<FunctionAction>) line ->
                new TeleportPlayer(line.getRequired(1, Location.class)));

        addLoader("SET_CURSOR_ITEM", (Line<FunctionAction>) line ->
                new SetPlayerCursorItem(line.getRequired(1, ItemStack.class)));

        addLoader("OPEN_ENDER_CHEST", (Line<FunctionAction>) line ->
                new OpenEnderChest());

        addLoader("CLOSE_INVENTORY", (Line<FunctionAction>) line ->
                new CloseInventory());

        addLoader("PLAYER_CACHE", (Line<FunctionAction>) line ->
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
    public @NotNull FunctionAction loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final ConfigLine line = scalar.toLine();
        final String actionId = line.getRequiredString(0);

        final ConfigLoader<? extends FunctionAction> loader = getLoader(actionId);
        if (loader == null) {
            throw new InvalidConfigurationException(line,
                    "Could not find action with name: " + CaseFormatter.toCamelCase(actionId));
        }

        FunctionAction action = loader.loadFromScalar(scalar);

        Integer repetitions = line.getInteger("repeat|repetitions")
                .filter(Predicates.isPositive(), "Repetitions must be greater than 0")
                .withDefault(null);

        Integer interval = line.get("interval|period", Ticks.class)
                .filter(repetitions != null, "Repetitions must be set to use interval delay")
                .map(Ticks::getCount)
                .withDefault(null);

        if (interval != null) {
            action = new PeriodicAction(plugin, action, interval, repetitions);
        } else if (repetitions != null) {
            action = new RepeatedAction(action, repetitions);
        }

        Integer delay = line.get("delay", Ticks.class)
                .map(Ticks::getCount)
                .withDefault(null);

        if (delay != null) {
            action = new DelayedAction(plugin, action, delay);
        }

        Double chance = line.getDouble("chance")
                .filter(Predicates.range(0, 1), "Chance must be a value between 0.0 to 1.0")
                .withDefault(null);

        if (chance != null) {
            action = new ChanceAction(action, chance);
        }

        return action;
    }

    @Override
    public @NotNull FunctionAction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiAction(sequence.getAll(FunctionAction.class));
    }

}
