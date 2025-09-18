package io.github.pigaut.voxel.core.function.condition.config;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.condition.block.*;
import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.core.function.condition.player.tool.*;
import io.github.pigaut.voxel.core.function.condition.server.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ConditionLoader extends AbstractLoader<Condition> {

    public ConditionLoader() {

        addLoader("ONLINE_PLAYERS", (Line<Condition>) line ->
                new OnlinePlayersCondition(line.getRequired(1, Amount.class)));

        addLoader("HAS_PERMISSION", (Line<Condition>) line ->
                new PlayerHasPermission(line.getRequiredString(1)));

        addLoader("PLAYER_HAS_PERMISSION", (Line<Condition>) line ->
                new PlayerHasPermission(line.getRequiredString(1)));

        addLoader("HAS_FLAG", (Line<Condition>) line ->
                new PlayerHasFlag(line.getRequiredString(1)));

        addLoader("PLAYER_HAS_FLAG", (Line<Condition>) line ->
                new PlayerHasFlag(line.getRequiredString(1)));

        addLoader("HAS_EXP", (Line<Condition>) line ->
                new PlayerHasExp(line.getRequired(1, Amount.class)));

        addLoader("PLAYER_HAS_EXP", (Line<Condition>) line ->
                new PlayerHasExp(line.getRequired(1, Amount.class)));

        addLoader("HAS_EXP_LEVEL", (Line<Condition>) line ->
                new PlayerHasExpLevel(line.getRequired(1, Amount.class)));

        addLoader("PLAYER_HAS_EXP_LEVEL", (Line<Condition>) line ->
                new PlayerHasExpLevel(line.getRequired(1, Amount.class)));

        addLoader("HAS_ITEM", (Line<Condition>) line ->
                new PlayerHasItem(line.getRequired(1, ItemStack.class)));

        addLoader("PLAYER_HAS_ITEM", (Line<Condition>) line ->
                new PlayerHasItem(line.getRequired(1, ItemStack.class)));

        addLoader("HAS_PLAYED_BEFORE", (Line<Condition>) line ->
                new PlayerHasPlayedBefore());

        addLoader("PLAYER_HAS_PLAYED_BEFORE", (Line<Condition>) line ->
                new PlayerHasPlayedBefore());

        addLoader("HAS_FREE_SLOT", (Line<Condition>) line ->
                new PlayerHasFreeSlot());

        addLoader("PLAYER_HAS_FREE_SLOT", (Line<Condition>) line ->
                new PlayerHasFreeSlot());

        addLoader("IS_FLYING", (Line<Condition>) line ->
                new PlayerIsFlying());

        addLoader("PLAYER_IS_FLYING", (Line<Condition>) line ->
                new PlayerIsFlying());

        addLoader("IS_SNEAKING", (Line<Condition>) line ->
                new PlayerIsSneaking());

        addLoader("PLAYER_IS_SNEAKING", (Line<Condition>) line ->
                new PlayerIsSneaking());

        addLoader("TOOL_IS_SIMILAR", (Line<Condition>) line ->
                new PlayerToolIsSimilar(line.getAll(1, ItemStack.class)));

        addLoader("TOOL_TYPE_IS_EQUAL", (Line<Condition>) line ->
                new PlayerToolTypeIsEqual(line.getAll(1, Material.class)));

        addLoader("TOOL_TYPE_EQUALS", (Line<Condition>) line ->
                new PlayerToolTypeIsEqual(line.getAll(1, Material.class)));

        addLoader("PLAYER_TOOL_TYPE_EQUALS", (Line<Condition>) line ->
                new PlayerToolTypeIsEqual(line.getAll(1, Material.class)));

        addLoader("TOOL_NAME_EQUALS", (Line<Condition>) line ->
                new PlayerToolNameEquals(line.getRequiredString(1)));

        addLoader("TOOL_LORE_LINE_EQUALS", (Line<Condition>) line -> {
            String lore = line.getRequiredString(1);
            int loreLine = line.getInteger("line|loreLine")
                    .filter(Predicates.isPositive(), "Lore line must be greater than or equal to 1")
                    .orThrow() - 1;
            return new PlayerToolLoreLineEquals(lore, loreLine);
        });

        addLoader("TOOL_LORE_EQUALS", (Line<Condition>) line ->
                new PlayerToolLoreEquals(line.getAll(1, String.class)));

        addLoader("TOOL_HAS_CUSTOM_MODEL", (Line<Condition>) line ->
                new PlayerToolHasCustomModel(line.getAll(1, Integer.class)));

        addLoader("TOOL_HAS_ENCHANT", (Line<Condition>) line ->
                new PlayerToolHasEnchant(
                        line.getRequired(1, Enchantment.class),
                        line.get("level|enchantLevel", Amount.class).throwOrElse(Amount.ANY)
                ));

        addLoader("PLACEHOLDER_EQUALS", (Line<Condition>) line -> {
            final String placeholder = line.getRequiredString("id|tag|placeholder|ph");

            final Amount amount = line.get(1, Amount.class).orElse(null);
            if (amount != null) {
                return new PlaceholderEqualsAmount(placeholder, amount);
            }

            final boolean ignoreCase = line.getBoolean("ignoreCase|ignore-case").throwOrElse(true);
            return new PlaceholderEqualsString(placeholder, line.getRequiredString(1), ignoreCase);
        });

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("PLAYER_HAS_MONEY", (Line<Condition>) line -> {
            if (economy == null) {
                throw new InvalidConfigurationException(line, "Missing vault or an economy plugin");
            }
            return new PlayerHasMoney(economy, line.getRequired(1, Amount.class));
        });

        addLoader("PROBABILITY", (Line<Condition>) line -> {
            double chance = line.getDouble(1)
                    .filter(Predicates.range(0, 1), "Chance must be a value between 0.0 and 1.0")
                    .orThrow();
            return new IsProbability(chance);
        });

        addLoader("CHANCE", (Line<Condition>) line -> {
            double chance = line.getDouble(1)
                    .filter(Predicates.range(0, 1), "Chance must be a value between 0.0 and 1.0")
                    .orThrow();
            return new IsProbability(chance);
        });

        addLoader("BLOCK_TYPE_EQUALS", (Line<Condition>) line ->
                new BlockTypeEquals(line.getAll(1, Material.class)));

    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid condition";
    }

    @Override
    public @NotNull Condition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final ConfigLine line = scalar.toLine();
        final String conditionId = line.getRequiredString(0);

        final ConfigLoader<? extends Condition> loader = getLoader(conditionId);
        if (loader == null) {
            throw new InvalidConfigurationException(line,
                    "Could not find condition with name: " + CaseFormatter.toCamelCase(conditionId));
        }

        return loader.loadFromScalar(scalar);
    }

    @Override
    public @NotNull Condition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiCondition(sequence.getAll(Condition.class));
    }

}
