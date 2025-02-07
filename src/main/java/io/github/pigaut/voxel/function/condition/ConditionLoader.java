package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.player.*;
import io.github.pigaut.voxel.function.condition.server.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
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

        addLoader("HAS_FLAG", (BranchLoader<Condition>) branch ->
                new PlayerHasFlag(branch.getString("flag", 1)));

        addLoader("HAS_EXP", (BranchLoader<Condition>) branch ->
                new PlayerHasExp(branch.get("amount", 1, Amount.class)));

        addLoader("HAS_EXP_LEVEL", (BranchLoader<Condition>) branch ->
                new PlayerHasExpLevel(branch.get("amount", 1, Amount.class)));

        addLoader("HAS_ITEM", (BranchLoader<Condition>) branch ->
                new PlayerHasItem(branch.get("item", 1, ItemStack.class)));

        addLoader("HAS_PLAYED_BEFORE", (BranchLoader<Condition>) branch ->
                new PlayerHasPlayedBefore());

        addLoader("IS_FLYING", (BranchLoader<Condition>) branch ->
                new PlayerIsFlying());

        addLoader("IS_SNEAKING", (BranchLoader<Condition>) branch ->
                new PlayerIsSneaking());

        addLoader("TOOL_IS_SIMILAR", (BranchLoader<Condition>) branch ->
                new PlayerToolIsSimilar(branch.get("item", 1, ItemStack.class)));

        addLoader("TOOL_HAS_ENCHANT", (BranchLoader<Condition>) branch ->
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

        final EconomyHook economy = SpigotServer.getEconomyHook();
        addLoader("HAS_MONEY", (BranchLoader<Condition>) branch -> {
            if (economy == null) {
                throw new InvalidConfigurationException(branch, "Missing vault or an economy plugin");
            }
            return new PlayerHasMoney(economy, branch.get("amount", 1, Amount.class));
        });

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
