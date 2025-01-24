package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.function.condition.player.*;
import io.github.pigaut.voxel.function.condition.server.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.enchantments.*;
import org.jetbrains.annotations.*;

public class ConditionLoader extends AbstractLoader<Condition> {

    public ConditionLoader() {
        addLoader("ONLINE_PLAYERS", ConstructorLoader.fromInteger(OnlinePlayersCondition::new));
        addLoader("MIN_ONLINE_PLAYERS", ConstructorLoader.fromInteger(MinOnlinePlayersCondition::new));
        addLoader("MAX_ONLINE_PLAYERS", ConstructorLoader.fromInteger(MaxOnlinePlayersCondition::new));

        addLoader("HAS_PERMISSION", ConstructorLoader.fromString(PlayerHasPermission::new));
        addLoader("HAS_FLAG", ConstructorLoader.fromString(PlayerHasFlag::new));

        addLoader("TOOL_HAS_ENCHANT", ConstructorLoader.from(Enchantment.class, PlayerToolHasEnchant::new));

        final EconomyHook economy = SpigotServer.getEconomyHook();
        if (economy != null) {
            addLoader("HAS_MONEY", ConstructorLoader.fromDouble(amount -> new PlayerHasMoney(economy, amount)));
        }
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "Could not load condition";
    }

    public ConfigLoader<? extends Condition> getLoader(ConfigField field, String id) {
        final ConfigLoader<? extends Condition> loader = getLoader(id);
        if (loader == null) {
            throw new InvalidConfigurationException(field,
                    "Could not find '" + StringFormatter.toConstantCase(id) + "' condition");
        }
        return loader;
    }

    @Override
    public @NotNull Condition loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        String conditionName = section.getString("condition");
        boolean negative = false;

        if (conditionName.startsWith("!")) {
            conditionName = conditionName.replace("!", conditionName);
            negative = true;
        }

        final Condition condition = getLoader(section, conditionName).loadFromSection(section);
        return negative ? new NegativeCondition(condition) : condition;
    }

    @Override
    public @NotNull Condition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        String conditionName = sequence.getString(0);
        boolean negative = false;

        if (conditionName.startsWith("!")) {
            conditionName = conditionName.replace("!", conditionName);
            negative = true;
        }

        final Condition condition = getLoader(sequence, conditionName).loadFromSequence(sequence);
        return negative ? new NegativeCondition(condition) : condition;
    }

}
