package io.github.pigaut.voxel.core.function.condition.config;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.condition.block.*;
import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.core.function.condition.player.tool.*;
import io.github.pigaut.voxel.core.function.condition.server.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.regex.*;

public class ConditionLoader extends AbstractLoader<Condition> {

    public ConditionLoader() {

        addLoader("ONLINE_PLAYERS", (BranchLoader<Condition>) branch ->
                new OnlinePlayersCondition(branch.get("amount", 1, Amount.class)));

        addLoader("HAS_PERMISSION", (BranchLoader<Condition>) branch ->
                new PlayerHasPermission(branch.getString("permission", 1)));

        addLoader("PLAYER_HAS_PERMISSION", (BranchLoader<Condition>) branch ->
                new PlayerHasPermission(branch.getString("permission", 1)));

        addLoader("HAS_FLAG", (BranchLoader<Condition>) branch ->
                new PlayerHasFlag(branch.getString("flag", 1)));

        addLoader("PLAYER_HAS_FLAG", (BranchLoader<Condition>) branch ->
                new PlayerHasFlag(branch.getString("flag", 1)));

        addLoader("HAS_EXP", (BranchLoader<Condition>) branch ->
                new PlayerHasExp(branch.get("amount", 1, Amount.class)));

        addLoader("PLAYER_HAS_EXP", (BranchLoader<Condition>) branch ->
                new PlayerHasExp(branch.get("amount", 1, Amount.class)));

        addLoader("HAS_EXP_LEVEL", (BranchLoader<Condition>) branch ->
                new PlayerHasExpLevel(branch.get("amount", 1, Amount.class)));

        addLoader("PLAYER_HAS_EXP_LEVEL", (BranchLoader<Condition>) branch ->
                new PlayerHasExpLevel(branch.get("amount", 1, Amount.class)));

        addLoader("HAS_ITEM", (BranchLoader<Condition>) branch ->
                new PlayerHasItem(branch.get("item", 1, ItemStack.class)));

        addLoader("PLAYER_HAS_ITEM", (BranchLoader<Condition>) branch ->
                new PlayerHasItem(branch.get("item", 1, ItemStack.class)));

        addLoader("HAS_PLAYED_BEFORE", (BranchLoader<Condition>) branch ->
                new PlayerHasPlayedBefore());

        addLoader("PLAYER_HAS_PLAYED_BEFORE", (BranchLoader<Condition>) branch ->
                new PlayerHasPlayedBefore());

        addLoader("HAS_FREE_SLOT", (BranchLoader<Condition>) branch ->
                new PlayerHasFreeSlot());

        addLoader("PLAYER_HAS_FREE_SLOT", (BranchLoader<Condition>) branch ->
                new PlayerHasFreeSlot());

        addLoader("IS_FLYING", (BranchLoader<Condition>) branch ->
                new PlayerIsFlying());

        addLoader("PLAYER_IS_FLYING", (BranchLoader<Condition>) branch ->
                new PlayerIsFlying());

        addLoader("IS_SNEAKING", (BranchLoader<Condition>) branch ->
                new PlayerIsSneaking());

        addLoader("PLAYER_IS_SNEAKING", (BranchLoader<Condition>) branch ->
                new PlayerIsSneaking());


        addLoader("TOOL_IS_SIMILAR", (BranchLoader<Condition>) branch ->
                new PlayerToolIsSimilar(branch.getScalar("item", 1).split("\\s*,\\s*").toList(ItemStack.class)));

        addLoader("TOOL_TYPE_IS_EQUAL", (BranchLoader<Condition>) branch ->
                new PlayerToolTypeIsEqual(branch.getScalar("material", 1).split("\\s*,\\s*").toList(Material.class)));

        addLoader("TOOL_TYPE_EQUALS", (BranchLoader<Condition>) branch ->
                new PlayerToolTypeIsEqual(branch.getScalar("material", 1).split("\\s*,\\s*").toList(Material.class)));

        addLoader("PLAYER_TOOL_TYPE_EQUALS", (BranchLoader<Condition>) branch ->
                new PlayerToolTypeIsEqual(branch.getScalar("material", 1).split("\\s*,\\s*").toList(Material.class)));

        addLoader("TOOL_NAME_EQUALS", (BranchLoader<Condition>) branch ->
                new PlayerToolNameEquals(branch.getString("name", 1)));

        addLoader("TOOL_LORE_LINE_EQUALS", (BranchLoader<Condition>) branch -> {
            final int line = branch.getInteger("line", 1);
            if (line < 1) {
                throw new InvalidConfigurationException(branch, "Lore line must be greater than or equal to 1");
            }
            return new PlayerToolLoreLineEquals(line - 1, branch.getString("lore", 2));
        });

        addLoader("TOOL_LORE_EQUALS", (BranchLoader<Condition>) branch ->
                new PlayerToolLoreEquals(branch.getScalar("lore", 1).split("\\s*,\\s*").toStringList()));

        addLoader("TOOL_HAS_CUSTOM_MODEL", (BranchLoader<Condition>) branch ->
                new PlayerToolHasCustomModel(branch.getScalar("model|models", 1).split("\\s*,\\s*").toIntegerList()));

        addLoader("TOOL_HAS_ENCHANT", (BranchLoader<Condition>) branch ->
                new PlayerToolHasEnchant(
                        branch.get("enchant|enchantment", 1, Enchantment.class),
                        branch.getOptional("level", 2, Amount.class).orElse(Amount.ANY)
                ));

        addLoader("PLAYER_TOOL_HAS_ENCHANT", (BranchLoader<Condition>) branch ->
                new PlayerToolHasEnchant(
                        branch.get("enchant|enchantment", 1, Enchantment.class),
                        branch.getOptional("level", 2, Amount.class).orElse(Amount.ANY)
                ));


        addLoader("IS_PLACEHOLDER", (BranchLoader<Condition>) branch -> {
            final String placeholder = branch.getString("placeholder", 1);
            final Amount amount = branch.getOptional("amount", 2, Amount.class).orElse(null);
            if (amount != null) {
                return new EqualsPlaceholderAmount(placeholder, amount);
            }
            return new EqualsPlaceholderString(placeholder, branch.getString("value", 2));
        });

        addLoader("PLACEHOLDER_EQUALS", (BranchLoader<Condition>) branch -> {
            final String placeholder = branch.getString("placeholder", 1);
            final Amount amount = branch.getOptional("amount", 2, Amount.class).orElse(null);
            if (amount != null) {
                return new EqualsPlaceholderAmount(placeholder, amount);
            }
            return new EqualsPlaceholderString(placeholder, branch.getString("value", 2));
        });

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("HAS_MONEY", (BranchLoader<Condition>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing vault or an economy plugin");
            }
            return new PlayerHasMoney(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("PLAYER_HAS_MONEY", (BranchLoader<Condition>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing vault or an economy plugin");
            }
            return new PlayerHasMoney(economy, branch.get("amount", 1, Amount.class));
        });

        addLoader("PROBABILITY", (BranchLoader<Condition>) branch -> {
            final double chance = branch.getDouble("chance", 1);
            if (chance < 0 || chance > 1) {
                throw new InvalidConfigurationException(branch, "Chance must be a value between 0.0 and 1.0");
            }
            return new IsProbability(chance);
        });

        addLoader("BLOCK_TYPE_EQUALS", (BranchLoader<Condition>) branch ->
                new BlockTypeEquals(branch.getScalar("material", 1).split("\\s*,\\s*").toList(Material.class)));

    }

    public ConfigLoader<? extends Condition> getLoader(ConfigField field, String id) {
        final ConfigLoader<? extends Condition> loader = getLoader(id);
        if (loader == null) {
            throw new InvalidConfigurationException(field,
                    "Could not find condition with name: " + StringFormatter.toCamelCase(id));
        }
        return loader;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid condition";
    }

    private static final Pattern INLINE_CONDITION_PATTERN = Pattern.compile("<([^>]*)>|(\\S+)");

    @Override
    public @NotNull Condition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final ConfigSequence splitScalar = scalar.split(INLINE_CONDITION_PATTERN);
        final ConfigLoader<? extends Condition> loader = getLoader(scalar, splitScalar.getString(0));
        return loader.loadFromSequence(splitScalar);
    }

    @Override
    public @NotNull Condition loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final ConfigLoader<? extends Condition> loader = getLoader(section, section.getString("condition"));
        return loader.loadFromSection(section);
    }

    @Override
    public @NotNull Condition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiCondition(sequence.getAll(Condition.class));
    }

}
